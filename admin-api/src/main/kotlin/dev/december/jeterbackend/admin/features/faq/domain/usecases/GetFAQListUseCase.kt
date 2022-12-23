package dev.december.jeterbackend.admin.features.faq.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.faq.domain.services.FAQService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.features.faq.domain.models.FAQ
import org.springframework.stereotype.Component


@Component

class GetFAQListUseCase(
    private val FAQService: FAQService
) : UseCase<GetFAQListParams, List<FAQ>> {
    override suspend fun invoke(params: GetFAQListParams): Data<List<FAQ>> {
        return FAQService.getAll(params.authority)
    }
}

data class GetFAQListParams(
    val authority: PlatformRole
)