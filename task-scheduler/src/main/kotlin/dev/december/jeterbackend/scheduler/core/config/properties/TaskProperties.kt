package dev.december.jeterbackend.scheduler.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "task")
data class TaskProperties(
    var poolSize: Int,
    var threadNamePrefix: String,
    var notificationTask: NotificationTaskProperties,
    var supplierTask: SupplierTaskProperties,
    var clientTask: ClientTaskProperties,
    var appointmentTask: AppointmentTaskProperties,
)
