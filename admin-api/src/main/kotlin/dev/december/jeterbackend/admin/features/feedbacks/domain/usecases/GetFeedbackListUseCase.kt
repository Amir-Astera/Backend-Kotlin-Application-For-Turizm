package dev.december.jeterbackend.admin.features.feedbacks.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.feedbacks.domain.services.FeedbackService
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.Feedback
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackSortField
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackStatus
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetFeedbackListUseCase(
    private val feedbackService: FeedbackService
) : UseCase<GetFeedbackListParams, Page<Feedback>> {
    override suspend fun invoke(params: GetFeedbackListParams): Data<Page<Feedback>> {
        return feedbackService.getList(
            params.sortField,
            params.sortDirection,
            params.page,
            params.size,
            params.statuses,
            params.createdFrom,
            params.createdTo,
            params.grades,
        )
    }
}

data class GetFeedbackListParams(
    val sortField: FeedbackSortField,
    val sortDirection: SortDirection,
    val page: Int,
    val size: Int,
    val statuses: Set<FeedbackStatus>,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?,
    val grades: Set<Float>?,
)