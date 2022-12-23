package dev.december.jeterbackend.admin.features.analytics.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounter
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounterSortDirection
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounterSortField
import org.springframework.data.domain.Page
import java.time.LocalDateTime

interface AnalyticsCounterService {
    suspend fun getToday(): Data<AnalyticsCounter>
    suspend fun getAll(
        sortField: AnalyticsCounterSortField,
        sortDirection: AnalyticsCounterSortDirection,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Page<AnalyticsCounter>>
    suspend fun getByDate(
        date: LocalDateTime
    ): Data<AnalyticsCounter>
}