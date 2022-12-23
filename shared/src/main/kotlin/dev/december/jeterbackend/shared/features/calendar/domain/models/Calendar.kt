package dev.december.jeterbackend.shared.features.calendar.domain.models

import java.time.LocalDate
import java.time.LocalDateTime

data class Calendar(
    val id: String,
    val firstDayOfMonth: LocalDate,
    val workingDays: List<LocalDate>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)