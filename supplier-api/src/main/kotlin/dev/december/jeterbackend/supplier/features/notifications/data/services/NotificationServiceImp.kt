package dev.december.jeterbackend.supplier.features.notifications.data.services

import com.google.firebase.messaging.FirebaseMessaging
import dev.december.jeterbackend.shared.features.notifications.data.entities.NotificationStatus
import dev.december.jeterbackend.shared.features.notifications.data.repositories.NotificationRepository
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.supplier.features.notifications.domain.services.NotificationService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.supplier.core.config.properties.TaskProperties
import dev.december.jeterbackend.supplier.features.tour.domain.errors.TourUpdateFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationServiceImp(
    private val dispatcher: CoroutineDispatcher,
    private val notificationRepository: NotificationRepository,
    private val firebaseMessaging: FirebaseMessaging,
    private val taskProperties: TaskProperties,
    ): NotificationService {
    override suspend fun sendSoonAppointmentNotifications() : Data<Unit> {
        return try {

            withContext(dispatcher) {

//                val notifications = notificationRepository
//                    .findByStatusAndRecipientTypeAndAppointmentDatetimeBetween(
//                        NotificationStatus.NOT_SENT,
//                        PlatformRole.SUPPLIER,
//                        LocalDateTime.now(),
//                        LocalDateTime.now() + taskProperties.notificationTask.timeBeforeAppointment
//                    )
//                val updatedNotifications = notifications.map { notification ->
//                    if(notification.user.supplier != null) {
//                        if(notification.user.supplier?.notify == true) {
//                            val notificationType = NotificationType.APPOINTMENT_SOON
//                            val notificationTitle = notificationType.title
//                            val notificationBody = String.format(
//                                notificationType.body,
//                                notification.sendersName
//                            )
//                            firebaseMessaging.send(
//                                Message.builder()
//                                    .putData("type", notificationType.data)
//                                    .setNotification(
//                                        Notification.builder()
//                                            .setTitle(notificationTitle)
//                                            .setBody(notificationBody)
//                                            .build()
//                                    )
//                                    .setToken(notification.user.supplier?.registrationToken)
//                                    .build()
//                            )
//                        }
//                    }
//                    notification.copy(
//                        status = NotificationStatus.HAVE_BEEN_SENT_BEFORE_TEM_MINUTES
//                    )
//                }
//                if (updatedNotifications.isEmpty()) {
//                    notificationRepository.saveAll(updatedNotifications)
//                }
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }

    override suspend fun cancelNotification(
        client: ClientEntity,
        supplier: SupplierEntity,
        reservationDate: LocalDateTime,
    ): Data<Unit> {
        return try {

            withContext(dispatcher) {
//                notificationRepository.deleteByUserAndAppointmentDatetimeAndRecipientType(
//                    client.user ?: return@withContext Data.Error(UserNotFoundFailure()),
//                    reservationDate,
//                    PlatformRole.CLIENT
//                )
//
//                notificationRepository.deleteByUserAndAppointmentDatetimeAndRecipientType(
//                    supplier.user ?: return@withContext Data.Error(UserNotFoundFailure()),
//                    reservationDate,
//                    PlatformRole.SUPPLIER
//                )
//
//                if(client.notify) {
//                    val notificationType = NotificationType.APPOINTMENT_CANCELED
//                    val notificationTitle = notificationType.title
//                    val notificationBody = String.format(
//                        notificationType.body,
//                        supplier.surName + " " + supplier.name + " " + supplier.patronymic,
//                        reservationDate.format(
//                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//                        )
//                    )
//                    firebaseMessaging.send(
//                        Message.builder()
//                            .putData("type", notificationType.data)
//                            .setNotification(
//                                Notification.builder()
//                                    .setTitle(notificationTitle)
//                                    .setBody(notificationBody)
//                                    .build()
//                            )
//                            .setToken(client.registrationToken)
//                            .build()
//                    )
//                }

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }

    override suspend fun clearNotifications() {
        notificationRepository.deleteAllByStatusAndRecipientType(
            NotificationStatus.HAVE_BEEN_SENT_BEFORE_TEM_MINUTES,
            PlatformRole.SUPPLIER
        )
    }

    override suspend fun confirmNotification(
        supplier: SupplierEntity,
        client: ClientEntity,
        reservationDate: LocalDateTime
    ): Data<Unit> {
        return try {

            withContext(dispatcher) {
//                val notificationForClient = NotificationEntity(
//                    sendersName = supplier.surName + " " + supplier.name + " " + supplier.patronymic,
//                    user = client.user ?: return@withContext Data.Error(UserNotFoundFailure()),
//                    appointmentDatetime = reservationDate,
//                    recipientType = PlatformRole.CLIENT
//                )
//
//                val notificationForSupplier = NotificationEntity(
//                    sendersName = client.fullName ?: "",
//                    user = supplier.user ?: return@withContext Data.Error(UserNotFoundFailure()),
//                    appointmentDatetime = reservationDate,
//                    recipientType = PlatformRole.SUPPLIER
//                )
//
//                notificationRepository.saveAll(listOf(notificationForClient, notificationForSupplier))
//
//                if(supplier.notify) {
//                    val notificationType = NotificationType.APPOINTMENT_CONFIRMED
//                    val notificationTitle = String.format(notificationType.title, client.fullName)
//                    val notificationBody = String.format(
//                        notificationType.body,
//                        reservationDate.format(
//                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//                        )
//                    )
//                    firebaseMessaging.send(
//                        Message.builder()
//                            .putData("type", notificationType.data)
//                            .setNotification(
//                                Notification.builder()
//                                    .setTitle(notificationTitle)
//                                    .setBody(notificationBody)
//                                    .build()
//                            )
//                            .setToken(client.registrationToken)
//                            .build()
//                    )
//                }
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }

    override suspend fun changeTimeNotification(
        client: ClientEntity,
        supplier: SupplierEntity,
        reservationDate: LocalDateTime,
    ): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                if (client.notify) {
//                    val notificationType = NotificationType.APPOINTMENT_CHANGED_TIME
//                    val notificationTitle = String.format(
//                        notificationType.title,
//                        supplier.surName + " " + supplier.name + " " + supplier.patronymic
//                    )
//                    val notificationBody = String.format(
//                        notificationType.body,
//                        supplier.surName + " " + supplier.name + " " + supplier.patronymic,
//                        reservationDate.format(
//                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//                        )
//                    )
//                    firebaseMessaging.send(
//                        Message.builder()
//                            .putData("type", notificationType.data)
//                            .setNotification(
//                                Notification.builder()
//                                    .setTitle(notificationTitle)
//                                    .setBody(notificationBody)
//                                    .build()
//                            )
//                            .setToken(client.registrationToken)
//                            .build()
//                    )
//                }
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(TourUpdateFailure())
        }
    }
}