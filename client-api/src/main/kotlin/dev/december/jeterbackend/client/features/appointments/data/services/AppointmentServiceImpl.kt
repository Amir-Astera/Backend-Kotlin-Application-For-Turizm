package dev.december.jeterbackend.client.features.appointments.data.services

import dev.december.jeterbackend.client.features.appointments.domain.errors.*
import dev.december.jeterbackend.client.features.appointments.domain.model.ClientAppointment
import dev.december.jeterbackend.client.features.appointments.domain.services.AppointmentService
import dev.december.jeterbackend.client.features.clients.domain.errors.ClientNotFoundFailure
import dev.december.jeterbackend.client.features.notifications.domain.services.NotificationService
import dev.december.jeterbackend.client.features.suppliers.domain.errors.CalendarNotFoundFailure
import dev.december.jeterbackend.client.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.appointments.data.entities.AppointmentEntity
import dev.december.jeterbackend.shared.features.appointments.data.repositories.AppointmentRepository
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.appointments.domain.models.CommunicationType
import dev.december.jeterbackend.shared.features.calendar.data.repositories.CalendarRepository
import dev.december.jeterbackend.shared.features.chats.data.entities.ChatEntity
import dev.december.jeterbackend.shared.features.chats.data.repositories.ChatRepository
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatArchiveStatus
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.domain.errors.SupplierNotFoundFailure
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime


@Service
class AppointmentServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val clientRepository: ClientRepository,
    private val appointmentRepository: AppointmentRepository,
    private val supplierRepository: SupplierRepository,
    private val calendarRepository: CalendarRepository,
    private val chatRepository: ChatRepository,
    private val supplierService: SupplierService,
    private val notificationService: NotificationService,
) : AppointmentService {

    override suspend fun create(
        reservationDate: LocalDateTime,
        communicationType: CommunicationType,
        description: String,
        userId: String,
        supplierId: String,
    ): Data<String> {
        return try {

            withContext(dispatcher) {
                val clientEntity = clientRepository.findByIdOrNull(userId)
                    ?: return@withContext Data.Error(ClientNotFoundFailure())
                val supplierEntity = supplierRepository.findByIdOrNull(supplierId)
                    ?: return@withContext Data.Error(SupplierNotFoundFailure())

                val firstDayOfMonth = reservationDate.toLocalDate().withDayOfMonth(1)
                val calendar = calendarRepository.findBySupplierIdAndFirstDayOfMonth(supplierId, firstDayOfMonth)
                    ?: return@withContext Data.Error(CalendarNotFoundFailure())

                if (!calendar.workingDays.contains(reservationDate.toLocalDate())) {
                    return@withContext Data.Error(SupplierNotWorkingFailure())
                }

                val appointmentEntity = appointmentRepository.save(
                    AppointmentEntity(
                        reservationDate = reservationDate,
                        communicationType = communicationType,
                        appointmentStatus = AppointmentStatus.CLIENT_SUBMITTED,
                        description = description,
                        client = clientEntity,
                        supplier = supplierEntity,
                    )
                )

                notificationService.create(
                    client = appointmentEntity.client,
                    supplier = appointmentEntity.supplier,
                    reservationDate = appointmentEntity.reservationDate
                )
                Data.Success(appointmentEntity.id)
            }


        } catch (e: Exception) {
            Data.Error(AppointmentCreateFailure())
        }
    }

    override suspend fun get(
        id: String,
        appointmentId: String
    ): Data<ClientAppointment> {
        return try {
            withContext(dispatcher) {

                val clientId = clientRepository.findByIdOrNull(id)?.id
                    ?: return@withContext Data.Error(ClientNotFoundFailure())

                val appointmentEntity = appointmentRepository.findByIdAndClientId(appointmentId, clientId)
                    ?: return@withContext Data.Error(AppointmentNotFoundFailure())

                val supplier = appointmentEntity.supplier.supplier()
                val appointment = appointmentEntity.convert<AppointmentEntity, ClientAppointment>(
                    mapOf("supplier" to supplier)
                )
                Data.Success(appointment)
            }
        } catch (e: Exception) {
            Data.Error(AppointmentGetFailure())
        }
    }

    override suspend fun getAll(
        id: String,
        statuses: Set<AppointmentStatus>,
        reservationDateFrom: LocalDateTime,
        reservationDateTo: LocalDateTime
    ): Data<Map<LocalDate, List<ClientAppointment>>> {
        return try {
            withContext(dispatcher) {

                val clientId = clientRepository.findByIdOrNull(id)?.id ?:
                return@withContext Data.Error(ClientNotFoundFailure())

                val appointmentsEntity = appointmentRepository
                    .findAllByClientIdAndAppointmentStatusInAndReservationDateBetweenOrderByReservationDate(
                        clientId, statuses, reservationDateFrom, reservationDateTo
                    )

                val appointments = appointmentsEntity.map { appointmentEntity ->
                    val supplier = appointmentEntity.supplier.supplier()
                    appointmentEntity.convert<AppointmentEntity, ClientAppointment>(
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

    override suspend fun getAllByClientAndSupplier(
        clientId: String,
        supplierId: String,
    ): Data<Map<LocalDate, List<ClientAppointment>>> {
        return try {
            withContext(dispatcher) {

                val clientIdVerified = clientRepository.findByIdOrNull(clientId)?.id
                    ?: return@withContext Data.Error(ClientNotFoundFailure())

                val supplierEntity = supplierRepository.findByIdOrNull(supplierId)
                    ?: return@withContext Data.Error(SupplierNotFoundFailure())

                if (supplierEntity.enableStatus != AccountEnableStatus.ENABLED || supplierEntity.status != SupplierStatus.APPROVED)
                    return@withContext Data.Error(SupplierNotFoundFailure())

                val appointmentsEntity =
                    appointmentRepository.findAllByClientIdAndSupplierId(clientIdVerified, supplierEntity.id)

                val appointments = appointmentsEntity.map { appointmentEntity ->
                    val supplier = appointmentEntity.supplier.supplier()
                    appointmentEntity.convert<AppointmentEntity, ClientAppointment>(
                        mapOf("supplier" to supplier)
                    )
                }

                val groupedAppointments =
                    appointments.sortedByDescending { it.reservationDate }.groupBy { it.reservationDate.toLocalDate() }

                Data.Success(groupedAppointments)
            }
        } catch (e: Exception) {
            Data.Error(AppointmentGetListFailure())
        }
    }

    override suspend fun delete(id: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                appointmentRepository.deleteById(id)
                Data.Success(Unit)
            }
        } catch (e: EmptyResultDataAccessException) {
            Data.Error(AppointmentNotFoundFailure())
        } catch (e: Exception) {
            Data.Error(AppointmentDeleteFailure())
        }
    }

    override suspend fun confirm(id: String): Data<String> {
        return try {

            withContext(dispatcher) {
                val oldEntity = appointmentRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(AppointmentNotFoundFailure())

                if (oldEntity.appointmentStatus == AppointmentStatus.CONFIRMED) {
                    return@withContext Data.Error(AppointmentConfirmationFailure(
                        message = "Appointment already confirmed!"))
                }

                val newAppointmentStatus =
                    when(oldEntity.appointmentStatus) {
                        AppointmentStatus.SUPPLIER_SUBMITTED -> AppointmentStatus.CONFIRMED
                        else -> return@withContext Data.Error(AppointmentConfirmationFailure())
                    }

                val newEntity = oldEntity.copy(
                    appointmentStatus = newAppointmentStatus,
                )
                appointmentRepository.save(newEntity)
                notificationService.confirm(
                    supplier = newEntity.supplier,
                    client = newEntity.client,
                    reservationDate = newEntity.reservationDate
                )

                if (chatRepository.findByClientIdAndSupplierId(newEntity.client.id, newEntity.supplier.id) == null) {
                    chatRepository.save(
                        ChatEntity(
                            client = newEntity.client,
                            supplier = newEntity.supplier,
                            archiveStatus = ChatArchiveStatus.UNARCHIVED
                        )
                    )
                }

                Data.Success(newEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(AppointmentUpdateFailure())
        }
    }

    override suspend fun cancel(id: String): Data<String> {
        return try {

            withContext(dispatcher) {
                val oldEntity = appointmentRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(AppointmentNotFoundFailure())

                if (oldEntity.appointmentStatus == AppointmentStatus.CANCELED) {
                    return@withContext Data.Error(AppointmentCancellationFailure(
                        message = "Appointment already canceled!"))
                } else if (oldEntity.appointmentStatus == AppointmentStatus.COMPLETED) {
                    return@withContext Data.Error(AppointmentCancellationFailure(
                        message = "Cannot cancel completed appointment!"))
                }

                val possibleAppointmentStates = setOf(
                    AppointmentStatus.CLIENT_SUBMITTED,
                    AppointmentStatus.SUPPLIER_SUBMITTED,
                    AppointmentStatus.CONFIRMED
                )

                val newEntity =
                    if (possibleAppointmentStates.contains(oldEntity.appointmentStatus)) {
                        oldEntity.copy(appointmentStatus = AppointmentStatus.CANCELED)
                    } else {
                        return@withContext Data.Error(AppointmentCancellationFailure())
                    }

                appointmentRepository.save(newEntity)
                notificationService.cancel(
                    client = newEntity.client,
                    supplier = newEntity.supplier,
                    reservationDate = newEntity.reservationDate
                )
                Data.Success(newEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(AppointmentUpdateFailure())
        }
    }

    override suspend fun complete(id: String): Data<String> {
        return try {
            withContext(dispatcher) {
                val oldEntity = appointmentRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(AppointmentNotFoundFailure())
                val newEntity = oldEntity.copy(
                    appointmentStatus = AppointmentStatus.COMPLETED,
                )
                appointmentRepository.save(newEntity)
                Data.Success(newEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(AppointmentUpdateFailure())
        }
    }

//    override suspend fun suggestAnotherTime(
//        id: String,
//        reservationDate: LocalDateTime
//    ): Data<String> {
//        return try {
//            withContext(dispatcher) {
//                val localDate = reservationDate.toLocalDate()
//                val localTime = reservationDate.toLocalTime()
//                val oldEntity = appointmentRepository.findByIdOrNull(id)
//                    ?: return@withContext Data.Error(AppointmentNotFoundFailure())
//
//                val supplierId = oldEntity.supplier.id
//
//                var freeTime: List<FreeTimeDto>? = null
//                supplierService.getSupplierFreeTime(supplierId, localDate).fold {
//                    freeTime = it; return@fold Data.Success(Unit)
//                }
//                freeTime?.find { freeTimeDto -> freeTimeDto.startTime == localTime }
//                    ?: return@withContext Data.Error(FreeTimeNotFoundFailure())
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
//                        AppointmentStatus.CLIENT_SUBMITTED
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
//                notificationService.changeTime(
//                    client = newEntity.client,
//                    supplier = newEntity.supplier,
//                    reservationDate = newEntity.reservationDate,
//                    oldReservationDate = oldEntity.reservationDate
//                )
//                Data.Success(newEntity.id)
//            }
//        } catch (e: Exception) {
//            Data.Error(AppointmentUpdateFailure())
//        }
//    }
}