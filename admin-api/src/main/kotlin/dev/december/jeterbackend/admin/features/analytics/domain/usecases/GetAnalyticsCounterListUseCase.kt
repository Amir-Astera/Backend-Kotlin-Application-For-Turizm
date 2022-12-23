package dev.december.jeterbackend.admin.features.analytics.domain.usecases

import dev.december.jeterbackend.admin.features.analytics.domain.services.AnalyticsCounterService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounter
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounterSortDirection
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounterSortField
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
class GetAnalyticsCounterListUseCase(
    private val analyticsCounterService: AnalyticsCounterService
) : UseCase<GetAnalyticsCounterListParams, Page<AnalyticsCounter>> {
  override suspend fun invoke(params: GetAnalyticsCounterListParams): Data<Page<AnalyticsCounter>> {
    return analyticsCounterService.getAll(
        params.sortField,
        params.sortDirection,
        params.page,
        params.size,
        params.createdFrom,
        params.createdTo
    )
  }
}

data class GetAnalyticsCounterListParams(
    val sortField: AnalyticsCounterSortField,
    val sortDirection: AnalyticsCounterSortDirection,
    val page: Int,
    val size: Int,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?
)