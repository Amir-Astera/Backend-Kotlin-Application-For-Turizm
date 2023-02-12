package dev.december.jeterbackend.scheduler.features.appointment.domain.usecases

import dev.december.jeterbackend.scheduler.core.config.properties.TaskProperties
import dev.december.jeterbackend.scheduler.features.appointment.domain.services.AppointmentService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

interface AppointmentTaskExecutorUseCase {
}


@Component
@Service
internal class AppointmentTaskExecutorUseCaseImpl(
    private val appointmentService: AppointmentService,
    private val taskProperties: TaskProperties,
    private val taskScheduler: TaskScheduler,
    private val logger: Logger,
) : AppointmentTaskExecutorUseCase {

    private inner class Cancel(): Runnable {
        override fun run() {
            runBlocking {
                launch {
                    try {
                        logger.info("Cancel appointments")
                        appointmentService.cancelOld()
                    } catch (e: Exception) {
                        logger.error(e.message)
                    }
                }
            }
        }

    }

    @PostConstruct
    fun scheduleRunnableWithCronTrigger() {
        if (taskProperties.appointmentTask.delete.toMillis() > 0) {
            taskScheduler.scheduleWithFixedDelay(
                Cancel(),
                (taskProperties.appointmentTask.delete.toMillis())
            )
        }
    }
}
