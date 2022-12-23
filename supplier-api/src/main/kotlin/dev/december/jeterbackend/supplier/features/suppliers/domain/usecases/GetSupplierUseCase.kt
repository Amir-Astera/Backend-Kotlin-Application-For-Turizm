package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import org.springframework.stereotype.Component

@Component
class GetSupplierUseCase (
    private val supplierService: SupplierService
) : UseCase<GetSupplierParams, Supplier> {
    override suspend fun invoke(params: GetSupplierParams): Data<Supplier> {
        return supplierService.get(params.userId)
    }
}

data class GetSupplierParams(
    val userId: String
)