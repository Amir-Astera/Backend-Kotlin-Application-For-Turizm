package dev.december.jeterbackend.supplier.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

data class NotificationTaskProperties(
    var timeBeforeAppointment: Duration,
    var sendNotificationPeriod: Duration,
    var clearNotificationPeriod: Duration,
)
