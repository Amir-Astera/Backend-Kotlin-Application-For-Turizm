package dev.december.jeterbackend.client.features.clients.domain.usecases

import dev.december.jeterbackend.client.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class DeleteSupplierFromFavoritesUseCase(
    private val clientService: ClientService
) : UseCase<DeleteFromFavoritesParams, Unit> {
    override suspend fun invoke(params: DeleteFromFavoritesParams): Data<Unit> {
        return clientService.deleteSupplierFromFavorites(
            params.userId,
            params.supplier
        )
    }
}

data class DeleteFromFavoritesParams(
    val userId: String,
    val supplier: String,
)