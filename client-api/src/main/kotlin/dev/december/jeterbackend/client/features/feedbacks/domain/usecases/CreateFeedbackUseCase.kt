package dev.december.jeterbackend.client.features.feedbacks.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.feedbacks.domain.services.FeedbackService
import dev.december.jeterbackend.client.features.feedbacks.presentation.dto.CreateFeedbackData
import org.springframework.stereotype.Component

@Component
class CreateFeedbackUseCase(
    private val feedbackService: FeedbackService
) : UseCase<CreateFeedbackParams, String> {
    override suspend fun invoke(params: CreateFeedbackParams): Data<String> {
        return feedbackService.create(
            params.userId,
            params.data.supplierId,
            params.data.grade,
            params.data.description,
        )
    }
}

data class CreateFeedbackParams(
    val userId: String,
    val data: CreateFeedbackData,


    )