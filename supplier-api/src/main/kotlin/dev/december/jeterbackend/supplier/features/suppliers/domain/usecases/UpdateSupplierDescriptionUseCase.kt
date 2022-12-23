package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component

@Component
class UpdateSupplierDescriptionUseCase (
    private val supplierService: SupplierService
) : UseCase<UpdateSupplierDescriptionParams, Unit> {
    override suspend fun invoke(params: UpdateSupplierDescriptionParams): Data<Unit> {
        return supplierService.updateDescription(
            params.userId,
            params.description
        )
    }
}

data class UpdateSupplierDescriptionParams(
    val userId: String,
    val description: String
)