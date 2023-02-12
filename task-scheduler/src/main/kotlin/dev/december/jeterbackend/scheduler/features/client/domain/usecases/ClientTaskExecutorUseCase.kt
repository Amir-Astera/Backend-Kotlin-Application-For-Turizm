package dev.december.jeterbackend.scheduler.features.client.domain.usecases

import dev.december.jeterbackend.scheduler.core.config.properties.TaskProperties
import dev.december.jeterbackend.scheduler.features.client.domain.services.ClientService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

interface ClientTaskExecutorUseCase {
}

@Component
@Service
internal class ClientTaskExecutorUseCaseImpl(
    private val clientService: ClientService,
    private val taskProperties: TaskProperties,
    private val taskScheduler: TaskScheduler,
    private val logger: Logger,
) : ClientTaskExecutorUseCase {

    private inner class Delete(): Runnable {
        override fun run() {
            runBlocking {
                launch {
                    try {
                        logger.info("Delete suppliers")
                        clientService.delete()
                    } catch (e: Exception) {
                        logger.error(e.message)
                    }
                }
            }
        }

    }

    @PostConstruct
    fun scheduleRunnableWithCronTrigger() {
        if (taskProperties.clientTask.delete.toMillis() > 0) {
            taskScheduler.scheduleWithFixedDelay(
                Delete(),
                (taskProperties.clientTask.delete.toMillis())
            )
        }
    }
}
