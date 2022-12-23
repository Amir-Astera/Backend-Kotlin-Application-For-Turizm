package dev.december.jeterbackend.admin.features.faq.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.faq.domain.services.FAQService
import org.springframework.stereotype.Component


@Component
class DeleteFAQUseCase(
    private val FAQService: FAQService
) : UseCase<DeleteFAQParams, Unit> {
    override suspend fun invoke(params: DeleteFAQParams): Data<Unit> {
        return FAQService.delete(params.id)
    }
}

data class DeleteFAQParams(
    val id: String
)