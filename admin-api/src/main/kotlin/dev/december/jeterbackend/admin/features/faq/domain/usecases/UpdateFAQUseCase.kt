package dev.december.jeterbackend.admin.features.faq.domain.usecases

import dev.december.jeterbackend.admin.features.faq.domain.services.FAQService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class UpdateFAQUseCase (
    private val FAQService: FAQService
) : UseCase<UpdateFAQParams, String> {
    override suspend fun invoke(params: UpdateFAQParams): Data<String> {
        return FAQService.update(params.id, params.name, params.description)
    }
}

data class UpdateFAQParams(
    val id: String,
    val name: String?,
    val description: String?
)