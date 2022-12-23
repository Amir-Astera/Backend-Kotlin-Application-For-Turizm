package dev.december.jeterbackend.admin.features.clients.domain.usecases

import dev.december.jeterbackend.admin.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class EnableClientUseCase(
    private val clientService: ClientService
) : UseCase<EnableClientParams, Unit> {
    override suspend fun invoke(params: EnableClientParams): Data<Unit> {
        return clientService.enable(params.id)
    }
}

data class EnableClientParams(
    val id: String,
)