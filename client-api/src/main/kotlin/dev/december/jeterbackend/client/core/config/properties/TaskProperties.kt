package dev.december.jeterbackend.supplier.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "task")
data class TaskProperties(
    var poolSize: Int,
    var threadNamePrefix: String,
    var notificationTask: NotificationTaskProperties,
    var supplierTask: SupplierTaskProperties
)
