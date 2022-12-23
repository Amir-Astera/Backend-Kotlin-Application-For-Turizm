package dev.december.jeterbackend.admin.features.feedbacks.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.feedbacks.domain.services.FeedbackService
import org.springframework.stereotype.Component

@Component
class DeleteFeedbackListUseCase(
    val feedbackService: FeedbackService
) : UseCase<DeleteFeedbackListParams, Unit> {
    override suspend fun invoke(params: DeleteFeedbackListParams): Data<Unit> {
        return feedbackService.deleteList(params.ids)
    }
}

data class DeleteFeedbackListParams(
    val ids: List<String>,
)