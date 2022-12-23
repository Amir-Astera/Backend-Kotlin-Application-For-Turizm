package dev.december.jeterbackend.client.features.clients.domain.usecases

import dev.december.jeterbackend.client.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class DeleteClientByUserIdUseCase(
    private val clientService: ClientService
) : UseCase<DeleteClientByUserIdParams, String> {
    override suspend fun invoke(params: DeleteClientByUserIdParams): Data<String> {
        return clientService.deleteByUserId(params.userId, params.signInProvider)
    }
}

data class DeleteClientByUserIdParams(
    val userId: String,
    val signInProvider: String?
)