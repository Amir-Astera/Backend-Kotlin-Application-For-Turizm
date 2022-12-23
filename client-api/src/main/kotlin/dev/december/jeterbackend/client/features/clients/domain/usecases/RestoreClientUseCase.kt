package dev.december.jeterbackend.client.features.clients.domain.usecases

import dev.december.jeterbackend.client.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class RestoreClientUseCase(private val service: ClientService): UseCase<RestoreClientParams, Unit> {
    override suspend fun invoke(params: RestoreClientParams): Data<Unit> {
        return service.restore(params.id, params.signInProvider)
    }
}

data class RestoreClientParams(
    val id: String,
    val signInProvider: String?
)