package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component

@Component
class UpdateEmailUseCase (
    private val supplierService: SupplierService
) : UseCase<UpdateEmailParams, Unit> {
    override suspend fun invoke(params: UpdateEmailParams): Data<Unit> {
        return supplierService.updateEmail(
            params.userId,
            params.email
        )
    }
}

data class UpdateEmailParams(
    val userId: String,
    val email: String,
)