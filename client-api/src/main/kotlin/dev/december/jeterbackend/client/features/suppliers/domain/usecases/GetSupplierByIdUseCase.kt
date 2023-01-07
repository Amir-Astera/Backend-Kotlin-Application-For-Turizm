package dev.december.jeterbackend.client.features.suppliers.domain.usecases

import dev.december.jeterbackend.client.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierProfileInformation
import org.springframework.stereotype.Component

@Component
class GetSupplierByIdUseCase (
    private val supplierService: SupplierService
) : UseCase<GetSupplierByIdParams, SupplierProfileInformation> {
    override suspend fun invoke(params: GetSupplierByIdParams): Data<SupplierProfileInformation> {
        return supplierService.getSupplier(params.clientId, params.supplierId)
    }
}

data class GetSupplierByIdParams(
    val clientId: String,
    val supplierId: String,
)