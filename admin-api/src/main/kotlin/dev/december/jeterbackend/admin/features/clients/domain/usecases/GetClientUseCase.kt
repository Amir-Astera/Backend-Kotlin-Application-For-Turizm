package dev.december.jeterbackend.admin.features.clients.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.features.clients.domain.models.ClientUser
import org.springframework.stereotype.Component

@Component
class GetClientUseCase (
    private val clientService: ClientService
) : UseCase<GetClientParams, ClientUser> {
    override suspend fun invoke(params: GetClientParams): Data<ClientUser> {
        return clientService.get(params.userId)
    }
}

data class GetClientParams(
    val userId: String
)