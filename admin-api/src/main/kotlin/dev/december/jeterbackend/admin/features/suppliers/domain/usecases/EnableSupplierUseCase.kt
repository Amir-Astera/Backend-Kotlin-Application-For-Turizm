package dev.december.jeterbackend.admin.features.suppliers.domain.usecases

import dev.december.jeterbackend.admin.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class EnableSupplierUseCase(
    private val supplierService: SupplierService
) : UseCase<EnableSupplierParams, Unit> {
    override suspend fun invoke(params: EnableSupplierParams): Data<Unit> {
        return supplierService.enable(params.id)
    }
}

data class EnableSupplierParams(
    val id: String,
)