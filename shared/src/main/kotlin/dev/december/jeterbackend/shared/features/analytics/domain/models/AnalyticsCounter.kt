package dev.december.jeterbackend.shared.features.analytics.domain.models

import java.time.LocalDate

data class AnalyticsCounter(
    val id: String,
    val supplierLogins: Int,
    val clientLogins: Int,
    val totalLogins: Int,
    val suppliersCreated: Int,
    val clientsCreated: Int,
    val totalCreated: Int,
    val transactionsCompleted: Int,
    val createdAt: LocalDate,
)