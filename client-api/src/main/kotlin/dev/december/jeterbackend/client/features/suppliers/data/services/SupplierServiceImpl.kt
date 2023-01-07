package dev.december.jeterbackend.client.features.suppliers.data.services

import dev.december.jeterbackend.client.features.appointments.domain.errors.AppointmentUpdateFailure
import dev.december.jeterbackend.client.features.clients.domain.errors.ClientNotFoundFailure
import dev.december.jeterbackend.client.features.suppliers.domain.errors.*
import dev.december.jeterbackend.client.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.client.features.suppliers.presentation.dto.FreeTimeDto
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.Gender
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.appointments.data.repositories.AppointmentRepository
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.calendar.data.repositories.CalendarRepository
import dev.december.jeterbackend.shared.features.calendar.domain.models.Calendar
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.FavoriteSupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.specifications.SupplierSpecification
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierProfileInformation
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierSortField
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierAgeRange
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierExperienceRange
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierPriceRange
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierRatingRange
import dev.december.jeterbackend.shared.features.tours.data.entities.ScheduleEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Service
class SupplierServiceImpl(
    private val supplierRepository: SupplierRepository,
    private val clientRepository: ClientRepository,
    private val appointmentRepository: AppointmentRepository,
    private val dispatcher: CoroutineDispatcher,
    private val calendarRepository: CalendarRepository,
    private val favoriteSupplierRepository: FavoriteSupplierRepository
    ) : SupplierService {

    override suspend fun getList(
        userId: String,
        page: Int,
        size: Int,
        searchField: String?,
        ratingFilter: SupplierRatingRange?,
        experienceFilter: SupplierExperienceRange?,
        ageFilter: SupplierAgeRange?,
        genderFilter: Gender?,
        priceFilter: SupplierPriceRange?,
    ): Data<Page<Supplier>> {
        return try {
            withContext(dispatcher) {

                val client = clientRepository.findByIdOrNull(userId)
                    ?: return@withContext Data.Error(ClientNotFoundFailure())

                val sortParams = SupplierSortField.CREATED_AT.getSortFields(SortDirection.DESC)

                val pageable = PageRequest.of(page, size, sortParams)

                val searchArray = searchField?.trim()?.split("\\s+".toRegex())?.toTypedArray()
                val ratingLowerBoundary = ratingFilter?.getRatingLowerBoundary(ratingFilter)
                val experienceLowerBoundary = experienceFilter?.getExperienceLowerBoundary(experienceFilter)
                val experienceUpperBoundary = experienceFilter?.getExperienceUpperBoundary(experienceFilter)
                val ageLowerBoundary = ageFilter?.getAgeLowerBoundaryIncl(ageFilter)
                val ageUpperBoundary = ageFilter?.getAgeUpperBoundaryNonIncl(ageFilter)
                val priceLowerBoundary = priceFilter?.getPriceLowerBoundary(priceFilter)
                val priceUpperBoundary = priceFilter?.getPriceUpperBoundary(priceFilter)

                val specifications =
                    Specification.where(SupplierSpecification.hasEnableStatus(AccountEnableStatus.ENABLED))
                        .and(SupplierSpecification.isInStatus(setOf(SupplierStatus.APPROVED)))
                        .and(SupplierSpecification.isInActivityStatus(setOf(AccountActivityStatus.ACTIVE)))
                        .and(SupplierSpecification.containsName(searchArray))
                        .and(SupplierSpecification.ratingIsGreaterOrEqualTo(ratingLowerBoundary))
                        .and(SupplierSpecification.experienceIsGreaterThan(experienceLowerBoundary))
                        .and(SupplierSpecification.experienceIsLessThan(experienceUpperBoundary))
                        .and(SupplierSpecification.ageIsGreaterThanOrEqualTo(ageLowerBoundary))
                        .and(SupplierSpecification.ageIsLessThan(ageUpperBoundary))
                        .and(SupplierSpecification.isGender(genderFilter))
                        .and(SupplierSpecification.priceIsGreaterThan(priceLowerBoundary))
                        .and(SupplierSpecification.priceIsLessThan(priceUpperBoundary))

                val entities = supplierRepository.findAll(specifications, pageable)

                val favoriteSupplierIds = favoriteSupplierRepository
                    .findAllByClientIdAndSupplierIsIn(client.id, entities.content)
                    .map { it.supplier.id }.toSet()

                val suppliers = entities.map {
                    it.supplier(favoriteSupplierIds.contains(it.id))
                }

                Data.Success(suppliers)
            }
        } catch (e: Exception) {
            println(e)
            Data.Error(SupplierListGetFailure())
        }
    }

    override suspend fun getSupplier(
        clientId: String,
        supplierId: String
    ): Data<SupplierProfileInformation> {
        return try {
            withContext(dispatcher) {
                val clientEntity = clientRepository.findByIdOrNull(clientId) ?: return@withContext Data.Error(ClientNotFoundFailure())

                val supplierEntity = supplierRepository.findByIdOrNull(supplierId)
                    ?: return@withContext Data.Error(SupplierNotFoundFailure())

                if (supplierEntity.enableStatus != AccountEnableStatus.ENABLED || supplierEntity.status != SupplierStatus.APPROVED)
                    return@withContext Data.Error(SupplierNotFoundFailure())

                val appointmentsEntity = appointmentRepository.findAllByClientIdAndSupplierId(clientEntity.id, supplierEntity.id)

                val isFavorite = favoriteSupplierRepository.existsBySupplierId(supplierId)

                val supplierProfile = SupplierProfileInformation(
                    numberOfAppointmentsWithClient = appointmentsEntity.size,
                    supplier = supplierEntity.supplier(isFavorite),
                )
                Data.Success(supplierProfile)
            }
        } catch (e: Exception) {
            Data.Error(SupplierGetFailure())
        }
    }

    override suspend fun getSupplierFreeTime(
        supplierId: String,
        date: LocalDate
    ): Data<List<FreeTimeDto>> {
        return try {
            withContext(dispatcher) {
                val supplierEntity = supplierRepository.findByIdOrNull(supplierId)
                    ?: return@withContext Data.Error(SupplierNotFoundFailure())

                if (supplierEntity.enableStatus != AccountEnableStatus.ENABLED || supplierEntity.status != SupplierStatus.APPROVED)
                    return@withContext Data.Error(SupplierNotFoundFailure())

                val appointments = appointmentRepository
                    .findAllBySupplierIdAndAppointmentStatusInAndReservationDateBetween(
                        supplierEntity.id,
                        setOf(AppointmentStatus.CONFIRMED, AppointmentStatus.CLIENT_SUBMITTED, AppointmentStatus.SUPPLIER_SUBMITTED),
                        date.atStartOfDay(),
                        date.plusDays(1).atStartOfDay().minusSeconds(1)
                    )

                val (workPeriod, breakPeriod) = when(date.dayOfWeek) {
                    DayOfWeek.MONDAY -> listOf(
                        supplierEntity.schedule?.monday ?: ScheduleEntity.defaultWorkPeriod,
                        supplierEntity.schedule?.mondayBreak ?: ScheduleEntity.defaultBreakPeriod
                    )
                    DayOfWeek.TUESDAY -> listOf(
                        supplierEntity.schedule?.tuesday ?: ScheduleEntity.defaultWorkPeriod,
                        supplierEntity.schedule?.tuesdayBreak ?: ScheduleEntity.defaultBreakPeriod
                    )
                    DayOfWeek.WEDNESDAY -> listOf(
                        supplierEntity.schedule?.wednesday ?: ScheduleEntity.defaultWorkPeriod,
                        supplierEntity.schedule?.wednesdayBreak ?: ScheduleEntity.defaultBreakPeriod
                    )
                    DayOfWeek.THURSDAY -> listOf(
                        supplierEntity.schedule?.thursday ?: ScheduleEntity.defaultWorkPeriod,
                        supplierEntity.schedule?.thursdayBreak ?: ScheduleEntity.defaultBreakPeriod
                    )
                    DayOfWeek.FRIDAY -> listOf(
                        supplierEntity.schedule?.friday ?: ScheduleEntity.defaultWorkPeriod,
                        supplierEntity.schedule?.fridayBreak ?: ScheduleEntity.defaultBreakPeriod
                    )
                    DayOfWeek.SATURDAY -> listOf(
                        supplierEntity.schedule?.saturday ?: ScheduleEntity.defaultWorkPeriod,
                        supplierEntity.schedule?.saturdayBreak ?: ScheduleEntity.defaultBreakPeriod
                    )
                    DayOfWeek.SUNDAY -> listOf(
                        supplierEntity.schedule?.sunday ?: ScheduleEntity.defaultWorkPeriod,
                        supplierEntity.schedule?.sundayBreak ?: ScheduleEntity.defaultBreakPeriod
                    )
                    else -> listOf(
                        ScheduleEntity.defaultWorkPeriod,
                        ScheduleEntity.defaultBreakPeriod
                    )
                }

                val (workPeriodFrom, workPeriodTo) = workPeriod.split("-").map { time ->
                    val (hourFrom, minuteFrom) = time.split(":").map { it.toInt() }
                    date.atTime(hourFrom, minuteFrom).toLocalTime()
                }
                val (breakPeriodFrom, breakPeriodTo) = breakPeriod.split("-").map { time ->
                    val (hourFrom, minuteFrom) = time.split(":").map { it.toInt() }
                    date.atTime(hourFrom, minuteFrom).toLocalTime()
                }

                val busyTime = appointments.map {
                    LocalTime.of(it.reservationDate.hour, it.reservationDate.minute)
                }.sorted().toMutableList()

                val freeTime = mutableListOf<FreeTimeDto>()

                var currentDatetime = workPeriodFrom

                val breakBeforeNextAppointment = ScheduleEntity.breakBeforeNextAppointment
                val appointmentDuration = ScheduleEntity.appointmentDuration

                while(currentDatetime < workPeriodTo) {
                    val timeBeforeBreak = ChronoUnit.MINUTES.between(currentDatetime, breakPeriodFrom)
                    if((timeBeforeBreak in 0 until appointmentDuration) ||
                        (currentDatetime in breakPeriodFrom..breakPeriodTo.minusSeconds(1))
                    ) {
                        currentDatetime = currentDatetime.plusMinutes(
                            ChronoUnit.MINUTES.between(currentDatetime, breakPeriodTo)
                        )
                        continue
                    }

                    if(busyTime.size != 0) {
                        val minutesDiff = ChronoUnit.MINUTES.between(currentDatetime, busyTime[0])
                        if (minutesDiff < (breakBeforeNextAppointment + appointmentDuration - 1)) {
                            currentDatetime = currentDatetime.plusMinutes(minutesDiff).plusMinutes(60)
                            busyTime.removeAt(0)
                            continue
                        }
                    }

                    val endTime = currentDatetime.plusMinutes(appointmentDuration)
                    if (ChronoUnit.MINUTES.between(endTime, workPeriodTo) >= 0) {
                        freeTime.add(FreeTimeDto(startTime = currentDatetime, endTime = endTime))
                    }
                    currentDatetime = currentDatetime.plusMinutes(appointmentDuration + breakBeforeNextAppointment)
                }

                Data.Success(freeTime)
            }
        } catch (e: Exception) {
            Data.Error(AppointmentUpdateFailure())
        }
    }

    override suspend fun getCalendar(supplierId: String, firstDayOfMonth: LocalDate): Data<Calendar> {
        return try {
            withContext(dispatcher) {
                val calendarEntity = calendarRepository.findBySupplierIdAndFirstDayOfMonth(
                    supplierId, firstDayOfMonth
                ) ?: return@withContext Data.Error(CalendarNotFoundFailure())
                Data.Success(calendarEntity.convert())
            }
        } catch (e: Exception) {
            Data.Error(CalendarGetFailure())
        }
    }
}