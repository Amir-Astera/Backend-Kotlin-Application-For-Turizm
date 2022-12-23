package dev.december.jeterbackend.admin.features.suppliers.domain.usecases

import dev.december.jeterbackend.admin.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class DisapproveSupplierUseCase(
    private val supplierService: SupplierService
    ) : UseCase<DisapproveSupplierParams, Unit> {
        override suspend fun invoke(params: DisapproveSupplierParams): Data<Unit> {
            return supplierService.disapprove(params.id)
        }
    }

data class DisapproveSupplierParams(
        val id: String
)