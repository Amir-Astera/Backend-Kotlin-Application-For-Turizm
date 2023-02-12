package dev.december.jeterbackend.scheduler.core.config.properties

import java.time.Duration

data class ClientTaskProperties(
    var delete: Duration,
    var minusDays: Int,
)