package dev.december.jeterbackend.supplier.features.feedbacks.domain.usecases

import dev.december.jeterbackend.supplier.features.feedbacks.domain.services.FeedbackService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackSupplier
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetFeedbackListUseCase(
    private val feedbackService: FeedbackService
) : UseCase<GetFeedbackListParams, Page<FeedbackSupplier>> {
    override suspend fun invoke(params: GetFeedbackListParams): Data<Page<FeedbackSupplier>> {
        return feedbackService.getList(
            params.id,
            params.page,
            params.size,
            params.createdFrom,
            params.createdTo,
        )
    }
}

data class GetFeedbackListParams(
    val id: String,
    val page: Int,
    val size: Int,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?
)