package dev.december.jeterbackend.supplier.features.feedbacks.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.feedbacks.domain.services.FeedbackService
import org.springframework.stereotype.Component

@Component
class CreateFeedbackUseCase(
    private val feedbackService: FeedbackService
) : UseCase<CreateFeedbackParams, String> {
    override suspend fun invoke(params: CreateFeedbackParams): Data<String> {
        return feedbackService.create(
            params.grade,
            params.description,
            params.clientId,
            params.supplierId,
        )
    }
}

data class CreateFeedbackParams(
    val grade: Float,
    val description: String?,
    val clientId: String,
    val supplierId: String,
)