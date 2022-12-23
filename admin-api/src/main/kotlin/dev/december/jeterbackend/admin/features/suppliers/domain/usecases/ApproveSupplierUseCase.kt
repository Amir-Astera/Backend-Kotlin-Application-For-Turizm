package dev.december.jeterbackend.admin.features.suppliers.domain.usecases

import dev.december.jeterbackend.admin.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class ApproveSupplierUseCase(
    private val supplierService: SupplierService
) : UseCase<ApproveSupplierParams, Unit> {
    override suspend fun invoke(params: ApproveSupplierParams): Data<Unit> {
        return supplierService.approve(params.id)
    }
}

data class ApproveSupplierParams(
    val id: String,
)