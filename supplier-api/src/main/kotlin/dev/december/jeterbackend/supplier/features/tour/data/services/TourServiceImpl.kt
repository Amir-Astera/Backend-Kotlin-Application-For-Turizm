package dev.december.jeterbackend.supplier.features.tour.data.services

import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.appointments.data.entities.extensions.appointment
import dev.december.jeterbackend.shared.features.appointments.data.repositories.AppointmentRepository
import dev.december.jeterbackend.shared.features.appointments.data.repositories.specification.AppointmentSpecification
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentSortField
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.calendar.data.repositories.CalendarRepository
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.domain.errors.SupplierNotFoundFailure
import dev.december.jeterbackend.shared.features.tours.data.entities.ScheduleEntity
import dev.december.jeterbackend.shared.features.tours.data.entities.TourEntity
import dev.december.jeterbackend.shared.features.tours.data.entities.extensions.tour
import dev.december.jeterbackend.shared.features.tours.data.repositories.TourRepository
import dev.december.jeterbackend.shared.features.tours.domain.models.Tour
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import dev.december.jeterbackend.shared.features.tours.domain.models.CommunicationType
import dev.december.jeterbackend.supplier.features.tour.domain.services.TourService
import dev.december.jeterbackend.supplier.features.tour.domain.errors.TourDeleteFailure
import dev.december.jeterbackend.supplier.features.tour.domain.errors.*
import dev.december.jeterbackend.supplier.features.tour.presentation.dto.FreeTimeDto
import dev.december.jeterbackend.supplier.features.tours.domain.errors.TourConfirmationFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.*
import java.time.temporal.ChronoUnit


@Service
class TourServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val tourRepository: TourRepository,
    private val calendarRepository: CalendarRepository,
    private val supplierRepository: SupplierRepository,
    private val appointmentRepository: AppointmentRepository
) : TourService {

    override suspend fun get(
        id: String,
        tourId: String
    ): Data<Tour> {
        return try {
            withContext(dispatcher) {
                val supplierId = supplierRepository.findByIdOrNull(id)?.id ?:
                    return@withContext Data.Error(SupplierNotFoundFailure())

                val tourEntity = tourRepository.findByIdAndSupplierId(tourId, supplierId) ?:
                    return@withContext Data.Error(TourNotFoundFailure())

                Data.Success(tourEntity.tour())
            }
        } catch (e: Exception) {
            Data.Error(TourGetFailure())
        }
    }

    override suspend fun getAll(
        id: String,
        statuses: Set<AppointmentStatus>,
        reservationDateFrom: LocalDateTime,
        reservationDateTo: LocalDateTime
    ): Data<Map<LocalDate, List<Tour>>> {
        return try {
            withContext(dispatcher) {
                val supplierId = supplierRepository.findByIdOrNull(id)?.id ?:
                    return@withContext Data.Error(SupplierNotFoundFailure())

                val toursEntity = tourRepository
                    .findAllBySupplierIdAndAppointmentStatusInAndReservationDateBetweenOrderByReservationDate(
                        supplierId, statuses, reservationDateFrom, reservationDateTo
                    )

                val tours = toursEntity.map { tourEntity ->
                    val supplier = tourEntity.supplier.supplier()
                    val client = tourEntity.client.client()
                    tourEntity.convert<TourEntity, Tour>(
                        mapOf(
                            "supplier" to supplier,
                            "client" to client,
                        )
                    )
                }

                val groupedtours = tours.groupBy { it.reservationDate.toLocalDate() }
                Data.Success(groupedtours)
            }
        } catch (e: Exception) {
            Data.Error(TourGetListFailure())
        }
    }

    override suspend fun delete(id: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                tourRepository.deleteById(id)
                Data.Success(Unit)
            }
        } catch (e: EmptyResultDataAccessException) {
            Data.Error(TourNotFoundFailure())
        } catch (e: Exception) {
            Data.Error(TourDeleteFailure())
        }
    }

//    override suspend fun confirm(id: String): Data<String> {
//        return try {
//
//            withContext(dispatcher) {
//                val oldEntity = tourRepository.findByIdOrNull(id)
//                    ?: return@withContext Data.Error(TourNotFoundFailure())
//
//                if (oldEntity.appointmentStatus == AppointmentStatus.CONFIRMED) {
//                    return@withContext Data.Error(TourConfirmationFailure(message = "Tour already confirmed!"))
//                }
//
//                val newTourStatus =
//                    when(oldEntity.appointmentStatus) {
//                        AppointmentStatus.CLIENT_SUBMITTED -> AppointmentStatus.CONFIRMED
//                        else -> return@withContext Data.Error(TourConfirmationFailure())
//                    }
//
//                val newEntity = oldEntity.copy(
//                    appointmentStatus = newTourStatus,
//                )
//                tourRepository.save(newEntity)
//                Data.Success(newEntity.id)
//            }
//        } catch (e: Exception) {
//            Data.Error(TourUpdateFailure())
//        }
//    }
//
//    override suspend fun cancel(id: String): Data<String> {
//        return try {
//
//            withContext(dispatcher) {
//                val oldEntity = tourRepository.findByIdOrNull(id)
//                    ?: return@withContext Data.Error(TourNotFoundFailure())
//
//                if (oldEntity.appointmentStatus == AppointmentStatus.CANCELED) {
//                    return@withContext Data.Error(
//                        TourCancellationFailure(
//                        message = "Tour already canceled!")
//                    )
//                } else if (oldEntity.appointmentStatus == AppointmentStatus.COMPLETED) {
//                    return@withContext Data.Error(
//                        TourCancellationFailure(
//                        message = "Cannot cancel completed appointment!")
//                    )
//                }
//
//                val possibleAppointmentStates = setOf(
//                    AppointmentStatus.CLIENT_SUBMITTED,
//                    AppointmentStatus.SUPPLIER_SUBMITTED,
//                    AppointmentStatus.CONFIRMED
//                )
//
//                val newEntity =
//                    if (possibleAppointmentStates.contains(oldEntity.appointmentStatus)) {
//                        oldEntity.copy(appointmentStatus = AppointmentStatus.CANCELED)
//                    } else {
//                        return@withContext Data.Error(TourCancellationFailure())
//                    }
//
//                tourRepository.save(newEntity)
//                Data.Success(newEntity.id)
//            }
//        } catch (e: Exception) {
//            Data.Error(TourUpdateFailure())
//        }
//    }
//
//    override suspend fun complete(id: String): Data<String> {
//        return try {
//            withContext(dispatcher) {
//                val oldEntity = tourRepository.findByIdOrNull(id)
//                    ?: return@withContext Data.Error(TourNotFoundFailure())
//                val newEntity = oldEntity.copy(
//                    appointmentStatus = AppointmentStatus.COMPLETED,
//                )
//                tourRepository.save(newEntity)
//                Data.Success(newEntity.id)
//            }
//        } catch (e: Exception) {
//            Data.Error(TourUpdateFailure())
//        }
//    }
//
//    override suspend fun suggestAnotherTime(
//        userId: String,
//        id: String,
//        reservationDate: LocalDateTime
//    ): Data<String> {
//        return try {
//            withContext(dispatcher) {
////                val oldEntity = appointmentRepository.findByIdOrNull(id)
////                    ?: return@withContext Data.Error(AppointmentNotFoundFailure())
////
////                if(oldEntity.supplier.user == null || userId != oldEntity.supplier.user?.id) {
////                    return@withContext Data.Error(SupplierNotFoundFailure())
////                }
////
////                val firstDayOfMonth = reservationDate.toLocalDate().withDayOfMonth(1)
////                val supplierId = oldEntity.supplier.id
////
////                val calendar = calendarRepository.findBySupplierIdAndFirstDayOfMonth(supplierId, firstDayOfMonth)
////                    ?: return@withContext Data.Error(CalendarNotFoundFailure())
////
////                if (!calendar.workingDays.contains(reservationDate.toLocalDate())) {
////                    return@withContext Data.Error(SupplierNotWorkingFailure())
////                }
////
////                val possibleAppointmentStates = setOf(
////                    AppointmentStatus.CLIENT_SUBMITTED,
////                    AppointmentStatus.SUPPLIER_SUBMITTED,
////                    AppointmentStatus.CONFIRMED
////                )
////                val newAppointmentStatus =
////                    if (possibleAppointmentStates.contains(oldEntity.appointmentStatus)) {
////                        AppointmentStatus.SUPPLIER_SUBMITTED
////                    } else {
////                        return@withContext Data.Error(AppointmentConfirmationFailure())
////                    }
////
////                val oldReservationDate = oldEntity.reservationDate
////
////                val newEntity = oldEntity.copy(
////                    appointmentStatus = newAppointmentStatus,
////                    reservationDate = reservationDate,
////                    oldReservationDate = oldReservationDate
////                )
////                appointmentRepository.save(newEntity)
//                Data.Success("")//newEntity.id
//            }
//        } catch (e: Exception) {
//            Data.Error(TourUpdateFailure())
//        }
//    }
//
//    override suspend fun getSupplierFreeTime(
//        userId: String,
//        date: LocalDate
//    ): Data<List<FreeTimeDto>> {
//        return try {
//            withContext(dispatcher) {
//                val supplier = supplierRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val appointments = appointmentRepository
//                    .findAllBySupplierIdAndAppointmentStatusInAndReservationDateBetween(
//                        supplier.id,
//                        setOf(AppointmentStatus.CONFIRMED, AppointmentStatus.CLIENT_SUBMITTED, AppointmentStatus.SUPPLIER_SUBMITTED),
//                        date.atStartOfDay(),
//                        date.plusDays(1).atStartOfDay().minusSeconds(1)
//                    )
//
//                val (workPeriod, breakPeriod) = when(date.dayOfWeek) {
//                    DayOfWeek.MONDAY -> listOf(
//                            supplier.schedule?.monday ?: ScheduleEntity.defaultWorkPeriod,
//                            supplier.schedule?.mondayBreak ?: ScheduleEntity.defaultBreakPeriod
//                        )
//                    DayOfWeek.TUESDAY -> listOf(
//                            supplier.schedule?.tuesday ?: ScheduleEntity.defaultWorkPeriod,
//                            supplier.schedule?.tuesdayBreak ?: ScheduleEntity.defaultBreakPeriod
//                        )
//                    DayOfWeek.WEDNESDAY -> listOf(
//                            supplier.schedule?.wednesday ?: ScheduleEntity.defaultWorkPeriod,
//                            supplier.schedule?.wednesdayBreak ?: ScheduleEntity.defaultBreakPeriod
//                        )
//                    DayOfWeek.THURSDAY -> listOf(
//                            supplier.schedule?.thursday ?: ScheduleEntity.defaultWorkPeriod,
//                            supplier.schedule?.thursdayBreak ?: ScheduleEntity.defaultBreakPeriod
//                        )
//                    DayOfWeek.FRIDAY -> listOf(
//                            supplier.schedule?.friday ?: ScheduleEntity.defaultWorkPeriod,
//                            supplier.schedule?.fridayBreak ?: ScheduleEntity.defaultBreakPeriod
//                        )
//                    DayOfWeek.SATURDAY -> listOf(
//                            supplier.schedule?.saturday ?: ScheduleEntity.defaultWorkPeriod,
//                            supplier.schedule?.saturdayBreak ?: ScheduleEntity.defaultBreakPeriod
//                        )
//                    DayOfWeek.SUNDAY -> listOf(
//                            supplier.schedule?.sunday ?: ScheduleEntity.defaultWorkPeriod,
//                            supplier.schedule?.sundayBreak ?: ScheduleEntity.defaultBreakPeriod
//                        )
//                    else -> listOf(
//                        ScheduleEntity.defaultWorkPeriod,
//                        ScheduleEntity.defaultBreakPeriod
//                    )
//                }
//
//                val (workPeriodFrom, workPeriodTo) = workPeriod.split("-").map { time ->
//                    val (hourFrom, minuteFrom) = time.split(":").map { it.toInt() }
//                    date.atTime(hourFrom, minuteFrom).toLocalTime()
//                }
//                val (breakPeriodFrom, breakPeriodTo) = breakPeriod.split("-").map { time ->
//                    val (hourFrom, minuteFrom) = time.split(":").map { it.toInt() }
//                    date.atTime(hourFrom, minuteFrom).toLocalTime()
//                }
//
//                val busyTime = appointments.map {
//                    LocalTime.of(it.reservationDate.hour, it.reservationDate.minute)
//                }.sorted().toMutableList()
//
//                val freeTime = mutableListOf<FreeTimeDto>()
//
//                var currentDatetime = workPeriodFrom
//
//                val breakBeforeNextAppointment = 20L
//                val appointmentDuration = 40L
//
//                while(currentDatetime < workPeriodTo) {
//                    val timeBeforeBreak = ChronoUnit.MINUTES.between(currentDatetime, breakPeriodFrom)
//                    if((timeBeforeBreak in 0 until appointmentDuration) ||
//                        (currentDatetime in breakPeriodFrom..breakPeriodTo.minusSeconds(1))
//                    ) {
//                        currentDatetime = currentDatetime.plusMinutes(
//                            ChronoUnit.MINUTES.between(currentDatetime, breakPeriodTo)
//                        )
//                        continue
//                    }
//
//                    if(busyTime.size != 0) {
//                        val minutesDiff = ChronoUnit.MINUTES.between(currentDatetime, busyTime[0])
//                        if (minutesDiff < (breakBeforeNextAppointment + appointmentDuration - 1)) {
//                            currentDatetime = currentDatetime.plusMinutes(minutesDiff).plusMinutes(60)
//                            busyTime.removeAt(0)
//                            continue
//                        }
//                    }
//
//                    val endTime = currentDatetime.plusMinutes(appointmentDuration)
//                    if (ChronoUnit.MINUTES.between(endTime, workPeriodTo) >= 0) {
//                        freeTime.add(FreeTimeDto(startTime = currentDatetime, endTime = endTime))
//                    }
//                    currentDatetime = currentDatetime.plusMinutes(appointmentDuration + breakBeforeNextAppointment)
//                }
//
//                Data.Success(freeTime)
//            }
//        } catch (e: Exception) {
//            Data.Error(TourUpdateFailure())
//        }
//    }

//    override suspend fun getListOfNotConfirmedAppointments(
//        userId: String,
//        page: Int,
//        size: Int,
//    ): Data<Page<Appointment>> {
//        return try {
//            withContext(dispatcher) {
//                val supplierId = supplierRepository.findByIdOrNull(userId)?.id ?:
//                    return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val sortParams = AppointmentSortField.RESERVATION_DATE.getSortFields(SortDirection.DESC)
//
//                val pageable = PageRequest.of(page, size, sortParams)
//
//                val specificationByUserRole =
//                    AppointmentSpecification.supplierJoinFilter(supplierId)
//
//                val specifications = Specification.where(specificationByUserRole)
//                    .and(AppointmentSpecification.hasStatus(setOf(AppointmentStatus.CLIENT_SUBMITTED)))
//
//                val appointmentsEntity = appointmentRepository.findAll(specifications, pageable)
//
//                val appointments = appointmentsEntity.map { it.appointment() }
//                Data.Success(appointments)
//            }
//        } catch (e: Exception) {
//            Data.Error(TourUpdateFailure())
//        }
//    }
}