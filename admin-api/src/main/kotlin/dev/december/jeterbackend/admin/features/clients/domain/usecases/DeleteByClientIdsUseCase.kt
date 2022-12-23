package dev.december.jeterbackend.admin.features.clients.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.clients.domain.services.ClientService
import org.springframework.stereotype.Component

@Component
class DeleteByClientIdsUseCase(
    private val clientService: ClientService
) : UseCase<DeleteByClientIdsParams, Unit> {
    override suspend fun invoke(params: DeleteByClientIdsParams): Data<Unit> {
        return clientService.deleteList(params.ids)
    }
}

data class DeleteByClientIdsParams(
    val ids: Set<String>
)