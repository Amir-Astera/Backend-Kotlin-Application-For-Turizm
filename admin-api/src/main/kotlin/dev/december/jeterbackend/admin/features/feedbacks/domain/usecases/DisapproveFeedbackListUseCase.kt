package dev.december.jeterbackend.admin.features.feedbacks.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.feedbacks.domain.services.FeedbackService
import org.springframework.stereotype.Component

@Component
class DisapproveFeedbackListUseCase(
    val feedbackService: FeedbackService
) : UseCase<DisapproveFeedbackListParams, Unit> {
    override suspend fun invoke(params: DisapproveFeedbackListParams): Data<Unit> {
        return feedbackService.disapproveList(params.ids)
    }
}

data class DisapproveFeedbackListParams(
    val ids: List<String>,
)