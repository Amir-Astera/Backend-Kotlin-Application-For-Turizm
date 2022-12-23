package dev.december.jeterbackend.supplier.features.suppliers.presentation.dto

import java.time.LocalDate

data class UpdateSupplierCalendar(
    val firstDayOfMonth: LocalDate,
    val workingDays: Set<LocalDate>,
)