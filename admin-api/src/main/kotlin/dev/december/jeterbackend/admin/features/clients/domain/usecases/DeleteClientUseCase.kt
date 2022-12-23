package dev.december.jeterbackend.admin.features.clients.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.clients.domain.services.ClientService
import org.springframework.stereotype.Component

@Component
class DeleteClientUseCase(
    private val clientService: ClientService
) : UseCase<DeleteClientParams, Unit> {
    override suspend fun invoke(params: DeleteClientParams): Data<Unit> {
        return clientService.delete(params.id)
    }
}

data class DeleteClientParams(
    val id: String
)