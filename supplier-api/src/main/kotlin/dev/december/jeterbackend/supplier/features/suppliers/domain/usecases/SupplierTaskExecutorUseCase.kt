package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.supplier.core.config.properties.TaskProperties
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

interface SupplierTaskExecutorUseCase {
}

@Component
@Service
internal class SupplierTaskExecutorUseCaseImpl(
    private val supplierService: SupplierService,
    private val taskProperties: TaskProperties,
    private val taskScheduler: TaskScheduler,
    private val logger: Logger,
) : SupplierTaskExecutorUseCase {
    private inner class ResetActivityStatus() : Runnable {
        override fun run() {
            runBlocking {
                launch {
                    try {
                        logger.info("Reset activity status started")
                        supplierService.resetActivityStatus()
                    } catch (e: Exception) {
                        logger.error(e.message)
                    }
                }
            }
        }
    }

    @PostConstruct
    fun scheduleRunnableWithCronTrigger() {
        taskScheduler.schedule(
            ResetActivityStatus(),
            CronTrigger(taskProperties.supplierTask.resetActivityStatus)
        )
    }
}
