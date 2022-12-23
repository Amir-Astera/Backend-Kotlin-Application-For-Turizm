package dev.december.jeterbackend.shared.features.analytics.domain.models

import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounterSortDirection
import org.springframework.data.domain.Sort

enum class AnalyticsCounterSortField(private val sortField: String) {
    CREATED_AT("createdAt");
    fun getSortField(sortDirection: AnalyticsCounterSortDirection): Sort {
        return if (sortDirection == AnalyticsCounterSortDirection.DESC) Sort.by(this.sortField).descending()
        else Sort.by(this.sortField).ascending()
    }
}