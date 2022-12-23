package dev.december.jeterbackend.supplier.features.appointments.presentation.dto

import java.time.LocalTime

data class FreeTimeDto(
    val startTime: LocalTime,
    val endTime: LocalTime
)