package dev.december.jeterbackend.client.features.feedbacks.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.feedbacks.domain.services.FeedbackService
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.Feedback
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class GetFeedbackListBySupplierUseCase(
    private val feedbackService: FeedbackService
) : UseCase<GetFeedbackListBySupplierParams, Page<Feedback>> {
    override suspend fun invoke(params: GetFeedbackListBySupplierParams): Data<Page<Feedback>> {
        return feedbackService.getListBySupplier(
            params.supplierId,
            params.page,
            params.size,
        )
    }
}

data class GetFeedbackListBySupplierParams(
    val supplierId: String,
    val page: Int,
    val size: Int,
)