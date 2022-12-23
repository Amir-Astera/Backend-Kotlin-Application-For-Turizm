package dev.december.jeterbackend.shared.features.tours.domain.models

data class SupplierPrice(
    val chatPerHour: Int?,
    val chatPerMinute: Int?,
    val audioPerHour: Int?,
    val audioPerMinute: Int?,
    val videoPerHour: Int?,
    val videoPerMinute: Int?,
)