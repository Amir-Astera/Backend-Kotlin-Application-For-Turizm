package dev.december.jeterbackend.supplier.features.notifications.domain.usecases

import com.google.firebase.messaging.FirebaseMessaging
import dev.december.jeterbackend.supplier.core.config.properties.TaskProperties
import dev.december.jeterbackend.supplier.features.notifications.domain.services.NotificationService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

interface NotificationTaskExecutorUseCase {
}

@Component
@Service
internal class NotificationTaskExecutorUseCaseImpl(
    private val notificationService: NotificationService,
    private val firebaseMessaging: FirebaseMessaging,
    private val taskProperties: TaskProperties,
    private val taskScheduler: TaskScheduler,
    private val logger: Logger,
) : NotificationTaskExecutorUseCase {
    private inner class SendSoonAppointmentNotifications() : Runnable {
        override fun run() {
            runBlocking {
                launch {
                    try {
                        logger.info("Soon notification pending started!")
                        notificationService.sendSoonAppointmentNotifications()
                    } catch (e: Exception) {
                        logger.error(e.message)
                    }
                }
            }
        }
    }

    private inner class ClearNotifications() : Runnable {
        override fun run() {
            runBlocking {
                launch {
                    try {
                        logger.info("Clear notification pending started!")
                        notificationService.clearNotifications()
                    } catch (e: Exception) {
                        logger.error(e.message)
                    }
                }
            }
        }
    }

    @PostConstruct
    fun scheduleRunnableWithCronTrigger() {
        if (taskProperties.notificationTask.sendNotificationPeriod.toMillis() > 0) {
            taskScheduler.scheduleWithFixedDelay(
                SendSoonAppointmentNotifications(),
                (taskProperties.notificationTask.sendNotificationPeriod.toMillis())
            )
        }
        if (taskProperties.notificationTask.clearNotificationPeriod.toMillis() > 0) {
            taskScheduler.scheduleWithFixedDelay(
                ClearNotifications(),
                (taskProperties.notificationTask.clearNotificationPeriod.toMillis())
            )
        }
    }
}
