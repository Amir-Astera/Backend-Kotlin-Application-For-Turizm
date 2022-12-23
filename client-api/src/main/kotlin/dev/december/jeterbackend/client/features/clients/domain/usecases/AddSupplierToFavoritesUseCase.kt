package dev.december.jeterbackend.client.features.clients.domain.usecases

import dev.december.jeterbackend.client.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class AddSupplierToFavoritesUseCase(
    private val clientService: ClientService
) : UseCase<AddToFavoritesParams, Unit> {
    override suspend fun invoke(params: AddToFavoritesParams): Data<Unit> {
        return clientService.addSupplierToFavorites(
            params.userId,
            params.supplier
        )
    }
}

data class AddToFavoritesParams(
    val userId: String,
    val supplier: String,
)