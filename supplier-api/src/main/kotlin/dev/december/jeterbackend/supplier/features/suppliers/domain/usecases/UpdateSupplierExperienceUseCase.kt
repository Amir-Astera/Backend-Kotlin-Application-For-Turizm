package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class UpdateSupplierExperienceUseCase (
    private val supplierService: SupplierService
) : UseCase<UpdateSupplierExperienceParams, Unit> {
    override suspend fun invoke(params: UpdateSupplierExperienceParams): Data<Unit> {
        return supplierService.updateExperience(
            params.userId,
            params.experience
        )
    }
}

data class UpdateSupplierExperienceParams(
    val userId: String,
    val experience: LocalDate?
)