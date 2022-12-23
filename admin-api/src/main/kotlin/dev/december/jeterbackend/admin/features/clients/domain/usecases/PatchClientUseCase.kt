package dev.december.jeterbackend.admin.features.clients.domain.usecases

import dev.december.jeterbackend.admin.features.clients.domain.services.ClientService
import dev.december.jeterbackend.admin.features.clients.presentation.dto.UpdateClientData
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class PatchClientUseCase(
    private val clientService: ClientService
): UseCase<PatchClientParams, String> {
    override suspend fun invoke(params: PatchClientParams): Data<String> {
        return clientService.update(
            params.userId,
            params.updateClientData
        )
    }
}

data class PatchClientParams(
    val userId: String,
    val updateClientData: UpdateClientData?,
)