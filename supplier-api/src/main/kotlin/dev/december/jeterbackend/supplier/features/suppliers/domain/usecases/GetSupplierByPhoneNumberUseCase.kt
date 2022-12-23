package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component

@Component
class GetSupplierByPhoneNumberUseCase (
    private val supplierService: SupplierService
) : UseCase<GetSupplierByPhoneParams, Boolean> {
    override suspend fun invoke(params: GetSupplierByPhoneParams): Data<Boolean> {
        return supplierService.getByPhone(params.phone)
    }
}

data class GetSupplierByPhoneParams(
    val phone: String
)