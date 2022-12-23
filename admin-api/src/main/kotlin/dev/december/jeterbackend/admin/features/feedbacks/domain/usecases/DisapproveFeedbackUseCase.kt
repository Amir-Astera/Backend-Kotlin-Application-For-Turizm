package dev.december.jeterbackend.admin.features.feedbacks.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.feedbacks.domain.services.FeedbackService
import org.springframework.stereotype.Component

@Component
class DisapproveFeedbackUseCase(
    private val feedbackService: FeedbackService
) : UseCase<DisapproveFeedbackParams, Unit> {
    override suspend fun invoke(params: DisapproveFeedbackParams): Data<Unit> {
        return feedbackService.disapprove(params.id)
    }
}

data class DisapproveFeedbackParams(
    val id: String,
)