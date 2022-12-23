package dev.december.jeterbackend.client.features.feedbacks.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.feedbacks.domain.services.FeedbackService
import org.springframework.stereotype.Component

@Component
class DeleteFeedbackUseCase(
    private val feedbackService: FeedbackService
) : UseCase<DeleteFeedbackParams, Unit> {
    override suspend fun invoke(params: DeleteFeedbackParams): Data<Unit> {
        return feedbackService.delete(params.id)
    }
}

data class DeleteFeedbackParams(
    val id: String,
)