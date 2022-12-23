package dev.december.jeterbackend.supplier.features.tour.presentation.dto

import java.time.LocalTime

data class FreeTimeDto(
    val startTime: LocalTime,
    val endTime: LocalTime
)