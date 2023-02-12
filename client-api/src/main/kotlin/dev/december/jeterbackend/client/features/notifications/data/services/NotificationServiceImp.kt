package dev.december.jeterbackend.client.features.notifications.data.services

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import dev.december.jeterbackend.client.core.config.properties.NotificationsProperties
import dev.december.jeterbackend.client.features.appointments.domain.errors.AppointmentUpdateFailure
import dev.december.jeterbackend.client.features.notifications.domain.services.NotificationService
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.notifications.data.entities.NotificationEntity
import dev.december.jeterbackend.shared.features.notifications.data.repositories.NotificationRepository
import dev.december.jeterbackend.shared.features.notifications.domain.models.NotificationProperty
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import org.slf4j.Logger

@Service
class NotificationServiceImp(
    private val dispatcher: CoroutineDispatcher,
    private val notificationRepository: NotificationRepository,
    private val firebaseMessaging: FirebaseMessaging,
    private val notificationsProperties: NotificationsProperties,
    private val logger: Logger,
): NotificationService {
    override suspend fun create(
        client: ClientEntity,
        supplier: SupplierEntity,
        reservationDate: LocalDateTime
    ): Data<Unit> {
        return try {
            withContext(dispatcher) {
                sendMessage(
                    notificationProperty = notificationsProperties.createAppointmentNotification,
                    supplier = supplier,
                    client = client,
                    reservationDate = reservationDate
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(AppointmentUpdateFailure())
        }
    }

    override suspend fun cancel(
        client: ClientEntity,
        supplier: SupplierEntity,
        reservationDate: LocalDateTime,
    ): Data<Unit> {
        return try {
            withContext(dispatcher) {
                notificationRepository.deleteBySupplierAndClientAndAppointmentDatetime(
                    supplier,
                    client,
                    reservationDate
                )

                sendMessage(
                    notificationProperty = notificationsProperties.cancelAppointmentNotification,
                    supplier = supplier,
                    client = client,
                    reservationDate = reservationDate
                )

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(AppointmentUpdateFailure())
        }
    }

    override suspend fun confirm(
        supplier: SupplierEntity,
        client: ClientEntity,
        reservationDate: LocalDateTime
    ): Data<Unit> {
        return try {
            withContext(dispatcher) {

                val notification = NotificationEntity(
                    supplier = supplier,
                    client = client,
                    appointmentDatetime = reservationDate
                )

                notificationRepository.save(notification)

                sendMessage(
                    notificationProperty = notificationsProperties.confirmAppointmentNotification,
                    client = client,
                    supplier = supplier,
                    reservationDate = reservationDate,
                )

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(AppointmentUpdateFailure())
        }
    }

    override suspend fun changeTime(
        client: ClientEntity,
        supplier: SupplierEntity,
        reservationDate: LocalDateTime,
        oldReservationDate: LocalDateTime,
    ): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val oldNotification = notificationRepository.findBySupplierAndClientAndAppointmentDatetime(
                    supplier,
                    client,
                    oldReservationDate
                )

                if (oldNotification != null) {
                    notificationRepository.save(
                        oldNotification.copy(
                            appointmentDatetime = reservationDate
                        )
                    )
                }

                sendMessage(
                    notificationProperty = notificationsProperties.changeAppointmentTimeNotification,
                    supplier = supplier,
                    client = client,
                    reservationDate = reservationDate
                )

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(AppointmentUpdateFailure())
        }
    }

    private suspend fun sendMessage(
        notificationProperty: NotificationProperty,
        supplier: SupplierEntity,
        client: ClientEntity,
        reservationDate: LocalDateTime
    ) {
        if(supplier.isNotifiable()) {
            val notificationType = notificationProperty.type
            val notificationContent = notificationProperty.getContentByLanguage(supplier.language)

            val notificationTitle = notificationContent.formatTitle(
                name = client.fullName,
                date = reservationDate,
            )
            val notificationBody = notificationContent.formatBody(
                name = client.fullName,
                date = reservationDate
            )

            sendMessage(
                supplier.registrationToken!!,
                notificationType,
                notificationTitle,
                notificationBody
            )
        }
    }

    private suspend fun sendMessage(
        registrationToken: String,
        type: String,
        title: String,
        body: String,
    ) {
        firebaseMessaging.send(
            Message.builder()
                .putData("type", type)
                .setNotification(
                    Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build()
                )
                .setToken(registrationToken)
                .build()
        )
    }

}