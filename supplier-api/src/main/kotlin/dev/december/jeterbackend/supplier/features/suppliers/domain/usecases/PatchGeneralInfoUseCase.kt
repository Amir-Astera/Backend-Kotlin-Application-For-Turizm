package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierGeneralInfoUpdate
import org.springframework.stereotype.Component

@Component
class PatchGeneralInfoUseCase(
    private val supplierService: SupplierService
) : UseCase<PatchGeneralInfoParams, String> {
    override suspend fun invoke(params: PatchGeneralInfoParams): Data<String> {
        return supplierService.updateGeneralInfo(
            params.userId,
            params.generalInfo
        )
    }
}

data class PatchGeneralInfoParams(
    val userId: String,
    val generalInfo: SupplierGeneralInfoUpdate
)