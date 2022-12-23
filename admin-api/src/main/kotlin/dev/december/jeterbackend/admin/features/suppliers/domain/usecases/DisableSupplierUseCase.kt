package dev.december.jeterbackend.admin.features.suppliers.domain.usecases

import dev.december.jeterbackend.admin.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class DisableSupplierUseCase(
    private val supplierService: SupplierService
) : UseCase<DisableSupplierParams, Unit> {
    override suspend fun invoke(params: DisableSupplierParams): Data<Unit> {
        return supplierService.disable(params.id)
    }
}

data class DisableSupplierParams(
    val id: String,
)