package dev.december.jeterbackend.client.features.suppliers.presentation.dto

import java.time.LocalTime

data class FreeTimeDto(
    val startTime: LocalTime,
    val endTime: LocalTime
)