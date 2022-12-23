package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component

@Component
class DeleteSupplierByUserIdUseCase(
    private val supplierService: SupplierService
) : UseCase<DeleteSupplierByUserIdParams, Unit> {
    override suspend fun invoke(params: DeleteSupplierByUserIdParams): Data<Unit> {
        return supplierService.deleteByUserId(params.id, params.signInProvider)
    }
}

data class DeleteSupplierByUserIdParams(
    val id: String,
    val signInProvider: String?
)