package dev.december.jeterbackend.client.features.clients.domain.usecases

import dev.december.jeterbackend.client.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import org.springframework.stereotype.Component

@Component
class GetClientUseCase(
    private val clientService: ClientService
) : UseCase<GetClientParams, Client> {
    override suspend fun invoke(params: GetClientParams): Data<Client> {
        return clientService.get(params.userId)
    }
}

data class GetClientParams(
    val userId: String
)