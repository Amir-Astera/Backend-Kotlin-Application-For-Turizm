package dev.december.jeterbackend.admin.features.analytics.domain.usecases

import dev.december.jeterbackend.admin.features.analytics.domain.services.AnalyticsCounterService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounter
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
class GetByDateAnalyticsCounterUseCase(
    private val analyticsCounterService: AnalyticsCounterService
) : UseCase<GetByDateAnalyticsCounterListParams, AnalyticsCounter> {
  override suspend fun invoke(params: GetByDateAnalyticsCounterListParams): Data<AnalyticsCounter> {
    return analyticsCounterService.getByDate(
        params.date
    )
  }
}

data class GetByDateAnalyticsCounterListParams(
    val date: LocalDateTime
)