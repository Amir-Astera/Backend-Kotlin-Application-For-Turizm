package dev.december.jeterbackend.client.features.clients.domain.usecases

import dev.december.jeterbackend.client.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class GetFavoriteSuppliersUseCase(
    private val clientService: ClientService
) : UseCase<GetFavoriteSuppliers, Page<Supplier>> {
    override suspend fun invoke(params: GetFavoriteSuppliers): Data<Page<Supplier>> {
        return clientService.getFavoriteSuppliers(params.userId, params.page, params.size)
    }
}

data class GetFavoriteSuppliers(
    val userId: String,
    val page: Int,
    val size: Int,
)