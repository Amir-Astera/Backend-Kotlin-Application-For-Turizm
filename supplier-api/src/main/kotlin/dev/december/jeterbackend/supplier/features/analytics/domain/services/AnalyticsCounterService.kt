package dev.december.jeterbackend.supplier.features.analytics.domain.services

import dev.december.jeterbackend.shared.core.results.Data


interface AnalyticsCounterService {
    suspend fun countLogin(): Data<Unit>
    suspend fun countCreate(): Data<Unit>
}