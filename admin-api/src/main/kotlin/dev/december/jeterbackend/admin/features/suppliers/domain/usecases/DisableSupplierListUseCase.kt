package dev.december.jeterbackend.admin.features.suppliers.domain.usecases

import dev.december.jeterbackend.admin.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class DisableSupplierListUseCase(
    private val supplierService: SupplierService
) : UseCase<DisableSupplierListParams, Unit> {
    override suspend fun invoke(params: DisableSupplierListParams): Data<Unit> {
        return supplierService.disableList(params.ids)
    }
}

data class DisableSupplierListParams(
    val ids: List<String>,
)