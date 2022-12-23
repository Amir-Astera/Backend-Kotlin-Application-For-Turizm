package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierEducationUpdate
import org.springframework.stereotype.Component

@Component
class UpdateEducationUseCase(
    private val supplierService: SupplierService
): UseCase<UpdateEducationParams, String> {
    override suspend fun invoke(params: UpdateEducationParams): Data<String> {
        return supplierService.get(params.userId).fold { supplier -> supplierService.updateEducation(supplier, params.educationId, params.data) }
    }
}

data class UpdateEducationParams(
    val userId: String,
    val educationId: String,
    val data: SupplierEducationUpdate
)