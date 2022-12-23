package dev.december.jeterbackend.shared.features.promocodes.presentation.dto

import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeStatus

data class UpdatePromocodeStatusData(
    val status: PromocodeStatus,
)
