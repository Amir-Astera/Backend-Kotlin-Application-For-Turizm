package dev.december.jeterbackend.admin.features.feedbacks.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.feedbacks.domain.services.FeedbackService
import org.springframework.stereotype.Component

@Component
class ApproveFeedbackUseCase(
    private val feedbackService: FeedbackService
) : UseCase<ApproveFeedbackParams, Unit> {
    override suspend fun invoke(params: ApproveFeedbackParams): Data<Unit> {
        return feedbackService.approve(params.id)
    }
}

data class ApproveFeedbackParams(
    val id: String,
)