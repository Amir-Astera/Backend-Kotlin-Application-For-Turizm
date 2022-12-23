package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.features.tours.domain.models.SupplierPrice
import org.springframework.stereotype.Component

@Component
class PatchPriceUseCase(
    private val supplierService: SupplierService
): UseCase<PatchPriceParams, String> {
    override suspend fun invoke(params: PatchPriceParams): Data<String> {
        return supplierService.updatePrice(params.userId, params.price)
    }
}

data class PatchPriceParams(
    val userId: String,
    val price: SupplierPrice
)