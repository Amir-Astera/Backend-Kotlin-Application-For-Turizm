package dev.december.jeterbackend.admin.features.faq.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.faq.domain.services.FAQService
import org.springframework.stereotype.Component

@Component
class DeleteFAQListUseCase (
    private val FAQService: FAQService
) : UseCase<DeleteFAQListParams, Unit> {
    override suspend fun invoke(params: DeleteFAQListParams): Data<Unit> {
        return FAQService.deleteList(params.ids)
    }
}

data class DeleteFAQListParams(
    val ids: List<String>
)