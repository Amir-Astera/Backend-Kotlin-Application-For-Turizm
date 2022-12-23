package dev.december.jeterbackend.admin.features.analytics.domain.usecases

import dev.december.jeterbackend.admin.features.analytics.domain.services.AnalyticsCounterService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounter
import org.springframework.stereotype.Component


@Component
class GetTodayAnalyticsCounterUseCase(
    private val analyticsCounterService: AnalyticsCounterService
) : UseCase<Unit, AnalyticsCounter> {
  override suspend fun invoke(params: Unit): Data<AnalyticsCounter> {
    return analyticsCounterService.getToday()
  }
}