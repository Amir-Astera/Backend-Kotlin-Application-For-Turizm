package dev.december.jeterbackend.scheduler.features.notifications.data.services

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import dev.december.jeterbackend.scheduler.core.config.properties.NotificationsProperties
import dev.december.jeterbackend.scheduler.core.config.properties.TaskProperties
import dev.december.jeterbackend.scheduler.core.config.security.firebase.FirebaseConfig
import dev.december.jeterbackend.scheduler.features.notifications.domain.errors.AppointmentUpdateFailure
import dev.december.jeterbackend.scheduler.features.notifications.domain.services.NotificationService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.notifications.data.entities.NotificationStatus
import dev.december.jeterbackend.shared.features.notifications.data.repositories.NotificationRepository
import dev.december.jeterbackend.shared.features.notifications.domain.models.NotificationProperty
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationServiceImp(
    private val dispatcher: CoroutineDispatcher,
    private val notificationRepository: NotificationRepository,
    private val firebaseConfig: FirebaseConfig,
    private val taskProperties: TaskProperties,
    private val notificationsProperties: NotificationsProperties,
    private val logger: Logger
): NotificationService {
    override suspend fun soonAppointment() : Data<Unit> {
        return try {

            withContext(dispatcher) {

                val notifications = notificationRepository
                    .findByStatusAndAppointmentDatetimeBetween(
                        NotificationStatus.NOT_SENT,
                        LocalDateTime.now(),
                        LocalDateTime.now() + taskProperties.notificationTask.timeBeforeAppointment
                    )
                val updatedNotifications = notifications.map { notification ->

                    logger.info("SENDING TO SUPPLIER")
                    sendMessageToSupplier(
                        notificationProperty = notificationsProperties.soonAppointmentNotification,
                        supplier = notification.supplier,
                        client = notification.client,
                    )


                    logger.info("SENDING TO CLIENT")
                    sendMessageToClient(
                        notificationProperty = notificationsProperties.soonAppointmentNotification,
                        supplier = notification.supplier,
                        client = notification.client
                    )

                    notification.copy(
                        status = NotificationStatus.HAVE_BEEN_SENT_BEFORE_TEM_MINUTES
                    )
                }
                if (updatedNotifications.isNotEmpty()) {
                    notificationRepository.saveAll(updatedNotifications)
                }
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(AppointmentUpdateFailure())
        }
    }

    override suspend fun clear() {
        notificationRepository.deleteAllByStatus(
            NotificationStatus.HAVE_BEEN_SENT_BEFORE_TEM_MINUTES
        )
    }

    private suspend fun sendMessageToClient(
        notificationProperty: NotificationProperty,
        supplier: SupplierEntity,
        client: ClientEntity
    ) {
        if(client.isNotifiable()) {
            val supplierFullName = supplier.getFullName()

            val notificationType = notificationProperty.type
            val notificationContent = notificationProperty.getContentByLanguage(client.language)

            val notificationTitle = notificationContent.formatTitle(
                name = supplierFullName,
            )
            val notificationBody = notificationContent.formatBody(
                name = supplierFullName,
            )

            sendMessage(
                firebaseConfig.firebaseMessaging(PlatformRole.SUPPLIER),
                client.registrationToken!!,
                notificationType,
                notificationTitle,
                notificationBody
            )
        }
    }

    private suspend fun sendMessageToSupplier(
        notificationProperty: NotificationProperty,
        supplier: SupplierEntity,
        client: ClientEntity
    ) {
        if(supplier.isNotifiable()) {
            val notificationType = notificationProperty.type
            val notificationContent = notificationProperty.getContentByLanguage(supplier.language)

            val notificationTitle = notificationContent.formatTitle(
                name = client.fullName,
            )
            val notificationBody = notificationContent.formatBody(
                name = client.fullName,
            )
            sendMessage(
                firebaseConfig.firebaseMessaging(PlatformRole.SUPPLIER),
                supplier.registrationToken!!,
                notificationType,
                notificationTitle,
                notificationBody
            )
        }
    }


    private suspend fun sendMessage(
        firebaseMessaging: FirebaseMessaging,
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