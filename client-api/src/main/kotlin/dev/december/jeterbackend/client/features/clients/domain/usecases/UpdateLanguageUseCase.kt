package dev.december.jeterbackend.client.features.clients.domain.usecases

import dev.december.jeterbackend.client.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.model.Language
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class UpdateLanguageUseCase (
    private val clientService: ClientService
) : UseCase<UpdateLanguageUseCaseParams, Unit> {
    override suspend fun invoke(params: UpdateLanguageUseCaseParams): Data<Unit> {
        return clientService.updateLanguage(
            params.userId,
            params.language,
        )
    }
}

data class UpdateLanguageUseCaseParams(
    val userId: String,
    val language: Language,
)