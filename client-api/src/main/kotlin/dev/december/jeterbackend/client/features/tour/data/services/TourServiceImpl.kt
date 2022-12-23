package dev.december.jeterbackend.client.features.tour.data.services

import dev.december.jeterbackend.client.features.tour.domain.errors.*
import dev.december.jeterbackend.client.features.tour.domain.model.ClientTour
import dev.december.jeterbackend.client.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.tours.data.repositories.TourRepository
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import dev.december.jeterbackend.shared.features.tours.domain.models.CommunicationType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class TourServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val appointmentRepository: TourRepository,
//    private val userRepository: UserRepository,
//    private val supplierRepository: SupplierRepository,
//    private val calendarRepository: CalendarRepository,
) : TourService {

    override suspend fun create(
        reservationDate: LocalDateTime,
        communicationType: CommunicationType,
        description: String,
        userId: String,
        supplierId: String,
    ): Data<String> {
        return try {

            withContext(dispatcher) {
//                val userEntity =
//                    userRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(ClientNotFoundFailure())
//                val clientEntity = userEntity.client ?: return@withContext Data.Error(ClientNotFoundFailure())
//                val supplierEntity =
//                    supplierRepository.findByIdOrNull(supplierId) ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val firstDayOfMonth = reservationDate.toLocalDate().withDayOfMonth(1)
//                val calendar = calendarRepository.findBySupplierIdAndFirstDayOfMonth(supplierId, firstDayOfMonth)
//                    ?: return@withContext Data.Error(CalendarNotFoundFailure())
//
//                if (!calendar.workingDays.contains(reservationDate.toLocalDate())) {
//                    return@withContext Data.Error(SupplierNotWorkingFailure())
//                }
//
//                val appointmentEntity = appointmentRepository.save(
//                    AppointmentEntity(
//                        reservationDate = reservationDate,
//                        communicationType = communicationType,
//                        appointmentStatus = AppointmentStatus.CLIENT_SUBMITTED,
//                        description = description,
//                        client = clientEntity,
//                        supplier = supplierEntity,
//                    )
//                )
                Data.Success("")//appointmentEntity.id
            }


        } catch (e: Exception) {
            Data.Error(TourCreateFailure())
        }
    }

    override suspend fun get(
        id: String,
        appointmentId: String
    ): Data<ClientTour> {
        return try {
            withContext(dispatcher) {

//                val user = userRepository.findByIdOrNull(id) ?:
//                return@withContext Data.Error(UserNotFoundFailure())
//
//                val clientId = user.client?.id ?:
//                return@withContext Data.Error(ClientNotFoundFailure())
//
//                val appointmentEntity = appointmentRepository.findByIdAndClientId(appointmentId, clientId)
//
//                val supplier = appointmentEntity.supplier.supplier()
//                val appointment = appointmentEntity.convert<AppointmentEntity, ClientAppointment>(
//                    mapOf("supplier" to supplier)
//                )
                Data.Success(ClientTour("", LocalDateTime.now(), LocalDateTime.now(), CommunicationType.CHAT, TourStatus.CLIENT_SUBMITTED, "", LocalDateTime.now(), 5, null))//appointment
            }
        } catch (e: Exception) {
            Data.Error(TourGetFailure())
        }
    }

//    override suspend fun getAll(
//        id: String,
//        statuses: Set<AppointmentStatus>,
//        reservationDateFrom: LocalDateTime,
//        reservationDateTo: LocalDateTime
//    ): Data<Map<LocalDate, List<ClientAppointment>>> {
//        return try {
//            withContext(dispatcher) {
//                val user = userRepository.findByIdOrNull(id) ?:
//                return@withContext Data.Error(UserNotFoundFailure())
//
//                val clientId = user.client?.id ?:
//                return@withContext Data.Error(ClientNotFoundFailure())
//
//                val appointmentsEntity = appointmentRepository
//                    .findAllByClientIdAndAppointmentStatusInAndReservationDateBetweenOrderByReservationDate(
//                        clientId, statuses, reservationDateFrom, reservationDateTo
//                    )
//
//                val appointments = appointmentsEntity.map { appointmentEntity ->
//                    val supplier = appointmentEntity.supplier.supplier()
//                    appointmentEntity.convert<AppointmentEntity, ClientAppointment>(
//                        mapOf(
//                            "supplier" to supplier,
//                        )
//                    )
//                }
//
//                val groupedAppointments = appointments.groupBy { it.reservationDate.toLocalDate() }
//
//                Data.Success(groupedAppointments)
//            }
//        } catch (e: Exception) {
//            Data.Error(AppointmentGetListFailure())
//        }
//    }

//    override suspend fun getAllByClientAndSupplier(
//        clientId: String,
//        supplierId: String,
//    ): Data<Map<LocalDate, List<ClientAppointment>>> {
//        return try {
//            withContext(dispatcher) {
//                val user = userRepository.findByIdOrNull(clientId) ?: return@withContext Data.Error(UserNotFoundFailure())
//
//                val clientIdVerified = user.client?.id ?: return@withContext Data.Error(ClientNotFoundFailure())
//
//                val supplierEntity = supplierRepository.findByIdOrNull(supplierId)
//                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                if (supplierEntity.enableStatus != AccountEnableStatus.ENABLED || supplierEntity.status != SupplierStatus.APPROVED)
//                    return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val appointmentsEntity =
//                    appointmentRepository.findAllByClientIdAndSupplierId(clientIdVerified, supplierEntity.id)
//
//                val appointments = appointmentsEntity.map { appointmentEntity ->
//                    val supplier = appointmentEntity.supplier.supplier()
//                    appointmentEntity.convert<AppointmentEntity, ClientAppointment>(
//                        mapOf("supplier" to supplier)
//                    )
//                }
//
//                val groupedAppointments =
//                    appointments.sortedByDescending { it.reservationDate }.groupBy { it.reservationDate.toLocalDate() }
//
//                Data.Success(groupedAppointments)
//            }
//        } catch (e: Exception) {
//            Data.Error(AppointmentGetListFailure())
//        }
//    }

    override suspend fun delete(id: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                appointmentRepository.deleteById(id)
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
                val oldEntity = appointmentRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(TourNotFoundFailure())

                if (oldEntity.tourStatus == TourStatus.CONFIRMED) {
                    return@withContext Data.Error(TourConfirmationFailure(
                        message = "Appointment already confirmed!"))
                }

                val newTourStatus =
                    when(oldEntity.tourStatus) {
                        TourStatus.SUPPLIER_SUBMITTED -> TourStatus.CONFIRMED
                        else -> return@withContext Data.Error(TourConfirmationFailure())
                    }

                val newEntity = oldEntity.copy(
                    tourStatus = newTourStatus,
                )
                appointmentRepository.save(newEntity)
                Data.Success(newEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }

    override suspend fun cancel(id: String): Data<String> {
        return try {

            withContext(dispatcher) {
                val oldEntity = appointmentRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(TourNotFoundFailure())

                if (oldEntity.tourStatus == TourStatus.CANCELED) {
                    return@withContext Data.Error(TourCancellationFailure(
                        message = "Appointment already canceled!"))
                } else if (oldEntity.tourStatus == TourStatus.COMPLETED) {
                    return@withContext Data.Error(TourCancellationFailure(
                        message = "Cannot cancel completed appointment!"))
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

                appointmentRepository.save(newEntity)
                Data.Success(newEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }

    override suspend fun complete(id: String): Data<String> {
        return try {
            withContext(dispatcher) {
                val oldEntity = appointmentRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(TourNotFoundFailure())
                val newEntity = oldEntity.copy(
                    tourStatus = TourStatus.COMPLETED,
                )
                appointmentRepository.save(newEntity)
                Data.Success(newEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }

    override suspend fun suggestAnotherTime(
        id: String,
        reservationDate: LocalDateTime
    ): Data<String> {
        return try {
            withContext(dispatcher) {
//                val oldEntity = appointmentRepository.findByIdOrNull(id)
//                    ?: return@withContext Data.Error(AppointmentNotFoundFailure())
//
//                val supplierId = oldEntity.supplier.id
//
//                val firstDayOfMonth = reservationDate.toLocalDate().withDayOfMonth(1)
//                val calendar = calendarRepository.findBySupplierIdAndFirstDayOfMonth(supplierId, firstDayOfMonth)
//                    ?: return@withContext Data.Error(CalendarNotFoundFailure())
//                if (!calendar.workingDays.contains(reservationDate.toLocalDate())) {
//                    return@withContext Data.Error(SupplierNotWorkingFailure())
//                }
//
//                val possibleAppointmentStates = setOf(
//                    AppointmentStatus.CLIENT_SUBMITTED,
//                    AppointmentStatus.SUPPLIER_SUBMITTED,
//                    AppointmentStatus.CONFIRMED
//                )
//
//                val newAppointmentStatus =
//                    if (possibleAppointmentStates.contains(oldEntity.appointmentStatus)) {
//                       AppointmentStatus.CLIENT_SUBMITTED
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
}