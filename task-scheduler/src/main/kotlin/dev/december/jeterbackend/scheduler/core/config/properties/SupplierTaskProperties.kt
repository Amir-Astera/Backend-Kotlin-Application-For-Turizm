package dev.december.jeterbackend.scheduler.core.config.properties

import java.time.Duration

data class SupplierTaskProperties(
    var resetActivityStatus: String,
    var delete: Duration,
    var minusDays: Int,
)