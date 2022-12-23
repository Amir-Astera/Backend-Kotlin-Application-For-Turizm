package dev.december.jeterbackend.supplier.features.tour.data.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.tours.data.repositories.TourRepository
import dev.december.jeterbackend.shared.features.tours.domain.models.Tour
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import dev.december.jeterbackend.shared.features.tours.domain.models.CommunicationType
import dev.december.jeterbackend.supplier.features.tour.domain.services.TourService
import dev.december.jeterbackend.supplier.features.tour.domain.errors.TourDeleteFailure
import dev.december.jeterbackend.supplier.features.tour.domain.errors.*
import dev.december.jeterbackend.supplier.features.tours.domain.errors.TourConfirmationFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.*


@Service
class TourServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val tourRepository: TourRepository,
//    private val userRepository: UserRepository,
//    private val calendarRepository: CalendarRepository,
) : TourService {

    override suspend fun get(
        id: String,
        tourId: String
    ): Data<Tour> {
        return try {
            withContext(dispatcher) {

//                val user = userRepository.findByIdOrNull(id) ?:
//                    return@withContext Data.Error(UserNotFoundFailure())
//
//                val supplierId = user.supplier?.id ?:
//                    return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val tourEntity = tourRepository.findByIdAndSupplierId(tourId, supplierId) ?:
//                    return@withContext Data.Error(tourNotFoundFailure())

                val temptour = Tour("", LocalDateTime.now(), LocalDateTime.now(), CommunicationType.CHAT, TourStatus.CLIENT_SUBMITTED, "", LocalDateTime.now(), 5, null)
                Data.Success(temptour)//tourEntity.tour()
            }
        } catch (e: Exception) {
            Data.Error(TourGetFailure())
        }
    }

    override suspend fun getAll(
        id: String,
        statuses: Set<TourStatus>,
        reservationDateFrom: LocalDateTime,
        reservationDateTo: LocalDateTime
    ): Data<Unit> {//Map<LocalDate, List<Tour>>
        return try {
            withContext(dispatcher) {

//                val user = userRepository.findByIdOrNull(id) ?:
//                    return@withContext Data.Error(UserNotFoundFailure())
//
//                val supplierId = user.supplier?.id ?:
//                    return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val toursEntity = tourRepository
//                    .findAllBySupplierIdAndAppointmentStatusInAndReservationDateBetweenOrderByReservationDate(
//                        supplierId, statuses, reservationDateFrom, reservationDateTo
//                    )
//
//                val tours = toursEntity.map { tourEntity ->
//                    val supplier = tourEntity.supplier.supplier()
//                    val client = tourEntity.client.client()
//                    tourEntity.convert<tourEntity, Tour>(
//                        mapOf(
//                            "supplier" to supplier,
//                            "client" to client,
//                        )
//                    )
//                }
//
//                val groupedtours = tours.groupBy { it.reservationDate.toLocalDate() }
                Data.Success(Unit)//groupedtours
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

    override suspend fun confirm(id: String): Data<String> {
        return try {

            withContext(dispatcher) {
                val oldEntity = tourRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(TourNotFoundFailure())

                if (oldEntity.tourStatus == TourStatus.CONFIRMED) {
                    return@withContext Data.Error(TourConfirmationFailure(message = "Tour already confirmed!"))
                }

                val newTourStatus =
                    when(oldEntity.tourStatus) {
                        TourStatus.CLIENT_SUBMITTED -> TourStatus.CONFIRMED
                        else -> return@withContext Data.Error(TourConfirmationFailure())
                    }

                val newEntity = oldEntity.copy(
                    tourStatus = newTourStatus,
                )
                tourRepository.save(newEntity)
                Data.Success(newEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }

    override suspend fun cancel(id: String): Data<String> {
        return try {

            withContext(dispatcher) {
                val oldEntity = tourRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(TourNotFoundFailure())

                if (oldEntity.tourStatus == TourStatus.CANCELED) {
                    return@withContext Data.Error(
                        TourCancellationFailure(
                        message = "Tour already canceled!")
                    )
                } else if (oldEntity.tourStatus == TourStatus.COMPLETED) {
                    return@withContext Data.Error(
                        TourCancellationFailure(
                        message = "Cannot cancel completed appointment!")
                    )
                }

                val possibleAppointmentStates = setOf(
                    TourStatus.CLIENT_SUBMITTED,
                    TourStatus.SUPPLIER_SUBMITTED,
                    TourStatus.CONFIRMED
                )

                val newEntity =
                    if (possibleAppointmentStates.contains(oldEntity.tourStatus)) {
                        oldEntity.copy(tourStatus = TourStatus.CANCELED)
                    } else {
                        return@withContext Data.Error(TourCancellationFailure())
                    }

                tourRepository.save(newEntity)
                Data.Success(newEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }

    override suspend fun complete(id: String): Data<String> {
        return try {
            withContext(dispatcher) {
                val oldEntity = tourRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(TourNotFoundFailure())
                val newEntity = oldEntity.copy(
                    tourStatus = TourStatus.COMPLETED,
                )
                tourRepository.save(newEntity)
                Data.Success(newEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }

    override suspend fun suggestAnotherTime(
        userId: String,
        id: String,
        reservationDate: LocalDateTime
    ): Data<String> {
        return try {
            withContext(dispatcher) {
//                val oldEntity = appointmentRepository.findByIdOrNull(id)
//                    ?: return@withContext Data.Error(AppointmentNotFoundFailure())
//
//                if(oldEntity.supplier.user == null || userId != oldEntity.supplier.user?.id) {
//                    return@withContext Data.Error(SupplierNotFoundFailure())
//                }
//
//                val firstDayOfMonth = reservationDate.toLocalDate().withDayOfMonth(1)
//                val supplierId = oldEntity.supplier.id
//
//                val calendar = calendarRepository.findBySupplierIdAndFirstDayOfMonth(supplierId, firstDayOfMonth)
//                    ?: return@withContext Data.Error(CalendarNotFoundFailure())
//
//                if (!calendar.workingDays.contains(reservationDate.toLocalDate())) {
//                    return@withContext Data.Error(SupplierNotWorkingFailure())
//                }
//
//                val possibleAppointmentStates = setOf(
//                    AppointmentStatus.CLIENT_SUBMITTED,
//                    AppointmentStatus.SUPPLIER_SUBMITTED,
//                    AppointmentStatus.CONFIRMED
//                )
//                val newAppointmentStatus =
//                    if (possibleAppointmentStates.contains(oldEntity.appointmentStatus)) {
//                        AppointmentStatus.SUPPLIER_SUBMITTED
//                    } else {
//                        return@withContext Data.Error(AppointmentConfirmationFailure())
//                    }
//
//                val oldReservationDate = oldEntity.reservationDate
//
//                val newEntity = oldEntity.copy(
//                    appointmentStatus = newAppointmentStatus,
//                    reservationDate = reservationDate,
//                    oldReservationDate = oldReservationDate
//                )
//                appointmentRepository.save(newEntity)
                Data.Success("")//newEntity.id
            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }

    override suspend fun getSupplierFreeTime(
        userId: String,
        date: LocalDate
    ): Data<Unit> {//List<FreeTimeDto>
        return try {
            withContext(dispatcher) {
//                val user = userRepository.findByIdOrNull(userId)
//                    ?: return@withContext Data.Error(UserNotFoundFailure())
//
//                val supplier = user.supplier ?: return@withContext Data.Error(SupplierNotFoundFailure())
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

                Data.Success(Unit)//freeTime
            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }

    override suspend fun getListOfNotConfirmedAppointments(
        userId: String,
        page: Int,
        size: Int,
    ): Data<Unit> {//Page<Tour>
        return try {
            withContext(dispatcher) {

//                val user = userRepository.findByIdOrNull(userId) ?:
//                    return@withContext Data.Error(UserNotFoundFailure())
//
//                val supplierId = user.supplier?.id ?:
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
                Data.Success(Unit)//appointments

            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }
}