package dev.december.jeterbackend.admin.features.feedbacks.domain.usecases

import dev.december.jeterbackend.admin.features.feedbacks.domain.services.FeedbackService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class UndefineFeedbackUseCase(
    private val feedbackService: FeedbackService
) : UseCase<UndefineFeedbackParams, Unit> {
    override suspend fun invoke(params: UndefineFeedbackParams): Data<Unit> {
        return feedbackService.undefine(params.id)
    }
}

data class UndefineFeedbackParams(
    val id: String,
)