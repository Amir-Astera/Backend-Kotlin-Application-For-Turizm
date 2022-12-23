package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import org.springframework.stereotype.Component

@Component
class GetSupplierByIdUseCase (
    private val supplierService: SupplierService
) : UseCase<GetSupplierByIdParams, Supplier> {
    override suspend fun invoke(params: GetSupplierByIdParams): Data<Supplier> {
        return supplierService.get(params.supplierId)
    }
}

data class GetSupplierByIdParams(
    val supplierId: String
)