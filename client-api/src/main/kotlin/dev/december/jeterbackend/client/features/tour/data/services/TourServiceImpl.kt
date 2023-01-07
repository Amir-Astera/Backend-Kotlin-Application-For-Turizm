package dev.december.jeterbackend.client.features.tour.data.services

import dev.december.jeterbackend.client.features.appointments.domain.errors.AppointmentGetListFailure
import dev.december.jeterbackend.client.features.appointments.domain.model.ClientAppointment
import dev.december.jeterbackend.client.features.clients.domain.errors.ClientNotFoundFailure
import dev.december.jeterbackend.client.features.tour.domain.errors.*
import dev.december.jeterbackend.client.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.appointments.data.entities.AppointmentEntity
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.calendar.data.repositories.CalendarRepository
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.domain.errors.SupplierNotFoundFailure
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
import dev.december.jeterbackend.shared.features.tours.data.entities.TourEntity
import dev.december.jeterbackend.shared.features.tours.data.repositories.TourRepository
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import dev.december.jeterbackend.shared.features.tours.domain.models.CommunicationType
import dev.december.jeterbackend.shared.features.tours.domain.models.Tour
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime


@Service
class TourServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val tourRepository: TourRepository,
    private val clientRepository: ClientRepository,
    private val supplierRepository: SupplierRepository,
    private val calendarRepository: CalendarRepository,
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
        tourId: String
    ): Data<Tour> {
        return try {
            withContext(dispatcher) {
                val clientId = clientRepository.findByIdOrNull(id) ?: return@withContext Data.Error(ClientNotFoundFailure())

                val appointmentEntity = tourRepository.findByIdAndClientId(tourId, clientId.id) ?: return@withContext Data.Error(TourNotFoundFailure())

                val supplier = appointmentEntity.supplier.supplier()
                val tour = appointmentEntity.convert<TourEntity, Tour>(
                    mapOf("supplier" to supplier)
                )
                Data.Success(tour)
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
    ): Data<Map<LocalDate, List<Appointment>>> {
        return try {
            withContext(dispatcher) {
                val clientId = clientRepository.findByIdOrNull(id) ?: return@withContext Data.Error(ClientNotFoundFailure())

                val appointmentsEntity = tourRepository.findAllByClientIdAndAppointmentStatusInAndReservationDateBetweenOrderByReservationDate(
                        clientId.id, statuses, reservationDateFrom, reservationDateTo)

                val appointments = appointmentsEntity.map { appointmentEntity ->
                    val supplier = appointmentEntity.supplier.supplier()
                    appointmentEntity.convert<TourEntity, Appointment>(
                        mapOf(
                            "supplier" to supplier,
                        )
                    )
                }

                val groupedAppointments = appointments.groupBy { it.reservationDate.toLocalDate() }

                Data.Success(groupedAppointments)
            }
        } catch (e: Exception) {
            Data.Error(AppointmentGetListFailure())
        }
    }

//    override suspend fun getAllByClientAndSupplier(
//        clientId: String,
//        supplierId: String,
//    ): Data<Map<LocalDate, List<Appointment>>> {
//        return try {
//            withContext(dispatcher) {
//                val clientIdVerified = clientRepository.findByIdOrNull(clientId)?.id ?: return@withContext Data.Error(ClientNotFoundFailure())
//
//                val supplierEntity = supplierRepository.findByIdOrNull(supplierId)
//                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                if (supplierEntity.enableStatus != AccountEnableStatus.ENABLED || supplierEntity.status != SupplierStatus.APPROVED)
//                    return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val appointmentsEntity =
//                    tourRepository.findAllByClientIdAndSupplierId(clientIdVerified, supplierEntity.id)
//
//                val appointments = appointmentsEntity.map { appointmentEntity ->
//                    val supplier = appointmentEntity.supplier.supplier()
//                    appointmentEntity.convert<TourEntity, Appointment>(
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

//    override suspend fun suggestAnotherTime(
//        id: String,
//        reservationDate: LocalDateTime
//    ): Data<String> {
//        return try {
//            withContext(dispatcher) {
////                val oldEntity = appointmentRepository.findByIdOrNull(id)
////                    ?: return@withContext Data.Error(AppointmentNotFoundFailure())
////
////                val supplierId = oldEntity.supplier.id
////
////                val firstDayOfMonth = reservationDate.toLocalDate().withDayOfMonth(1)
////                val calendar = calendarRepository.findBySupplierIdAndFirstDayOfMonth(supplierId, firstDayOfMonth)
////                    ?: return@withContext Data.Error(CalendarNotFoundFailure())
////                if (!calendar.workingDays.contains(reservationDate.toLocalDate())) {
////                    return@withContext Data.Error(SupplierNotWorkingFailure())
////                }
////
////                val possibleAppointmentStates = setOf(
////                    AppointmentStatus.CLIENT_SUBMITTED,
////                    AppointmentStatus.SUPPLIER_SUBMITTED,
////                    AppointmentStatus.CONFIRMED
////                )
////
////                val newAppointmentStatus =
////                    if (possibleAppointmentStates.contains(oldEntity.appointmentStatus)) {
////                       AppointmentStatus.CLIENT_SUBMITTED
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
}