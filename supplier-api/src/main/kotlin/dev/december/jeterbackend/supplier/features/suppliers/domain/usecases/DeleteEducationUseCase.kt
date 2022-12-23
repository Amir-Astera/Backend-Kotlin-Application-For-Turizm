package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component

@Component
class DeleteEducationUseCase(
    private val supplierService: SupplierService
): UseCase<DeleteEducationParams, String> {
    override suspend fun invoke(params: DeleteEducationParams): Data<String> {
        return supplierService.get(params.userId).fold { supplier -> supplierService.deleteEducation(supplier, params.educationId) }
    }
}

data class DeleteEducationParams(
    val userId: String,
    val educationId: String
)