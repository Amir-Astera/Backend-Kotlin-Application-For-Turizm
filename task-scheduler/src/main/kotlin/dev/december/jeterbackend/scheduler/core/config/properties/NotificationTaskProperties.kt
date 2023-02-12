package dev.december.jeterbackend.scheduler.core.config.properties

import java.time.Duration

data class NotificationTaskProperties(
    val timeBeforeAppointment: Duration,
    val sendNotificationPeriod: Duration,
    val clearNotificationPeriod: Duration
)
