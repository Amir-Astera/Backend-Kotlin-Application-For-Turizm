package dev.december.jeterbackend.supplier.core.config.properties

import dev.december.jeterbackend.shared.features.notifications.domain.models.NotificationProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "notifications")
data class NotificationsProperties (
    val createAppointmentNotification: NotificationProperty,
    val confirmAppointmentNotification: NotificationProperty,
    val changeAppointmentTimeNotification: NotificationProperty,
    val cancelAppointmentNotification: NotificationProperty
)