package dev.december.jeterbackend.admin.features.faq.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.faq.domain.services.FAQService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import org.springframework.stereotype.Component



@Component
class CreateFAQUseCase(
    private val FAQService: FAQService
) : UseCase<CreateFAQParams, String> {
    override suspend fun invoke(params: CreateFAQParams): Data<String> {
        return FAQService.create(params.title, params.description, params.authority)
    }
}

data class CreateFAQParams(
    val title: String,
    val description: String,
    val authority: PlatformRole
)