package dev.december.jeterbackend.supplier.features.notifications.data.services

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.notifications.data.entities.NotificationEntity
import dev.december.jeterbackend.shared.features.notifications.data.repositories.NotificationRepository
import dev.december.jeterbackend.shared.features.notifications.domain.models.NotificationProperty
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.supplier.core.config.properties.NotificationsProperties
import dev.december.jeterbackend.supplier.features.appointments.domain.errors.AppointmentUpdateFailure
import dev.december.jeterbackend.supplier.features.notifications.domain.services.NotificationService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationServiceImp(
    private val dispatcher: CoroutineDispatcher,
    private val notificationRepository: NotificationRepository,
    private val firebaseMessaging: FirebaseMessaging,
    private val notificationsProperties: NotificationsProperties,
    private val logger: Logger,
): NotificationService {

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
                    reservationDate,
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

                logger.info("SENDING NOTIFICATION")

                sendMessage(
                    notificationProperty = notificationsProperties.confirmAppointmentNotification,
                    supplier = supplier,
                    client = client,
                    reservationDate = reservationDate,
                )

                Data.Success(Unit)
            }
        } catch (e: Exception) {
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

                val notification = notificationRepository.findBySupplierAndClientAndAppointmentDatetime(
                    supplier,
                    client,
                    oldReservationDate
                )
                if (notification != null) {
                    notificationRepository.save(
                        notification.copy(
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
            Data.Error(AppointmentUpdateFailure())
        }
    }

    private suspend fun sendMessage(
        notificationProperty: NotificationProperty,
        supplier: SupplierEntity,
        client: ClientEntity,
        reservationDate: LocalDateTime
    ) {
        if(client.isNotifiable()) {
            val supplierFullName = supplier.getFullName()

            val notificationType = notificationProperty.type
            val notificationContent = notificationProperty.getContentByLanguage(client.language)

            val notificationTitle = notificationContent.formatTitle(
                name = supplierFullName,
                date = reservationDate,
            )
            val notificationBody = notificationContent.formatBody(
                name = supplierFullName,
                date = reservationDate
            )

            sendMessage(
                client.registrationToken!!,
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
        try {
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
        } catch (e: Exception) {
            logger.error(e.message)
        }
    }

}