package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierEducation
import org.springframework.stereotype.Component

@Component
class AddEducationUseCase(
    private val supplierService: SupplierService
): UseCase<AddEducationParams, String> {
    override suspend fun invoke(params: AddEducationParams): Data<String> {
        return supplierService.addEducation(params.userId, params.education)
    }
}

data class AddEducationParams(
    val userId: String,
    val education: SupplierEducation
)