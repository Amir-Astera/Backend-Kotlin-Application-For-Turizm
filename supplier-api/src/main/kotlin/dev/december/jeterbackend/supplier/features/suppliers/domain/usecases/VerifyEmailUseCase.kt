package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component

@Component
data class VerifyEmailUseCase(
    private val supplierService: SupplierService
) : UseCase<VerifyEmailParams, Unit> {
    override suspend fun invoke(params: VerifyEmailParams): Data<Unit> {
        return supplierService.verifyEmail(params.userId)
    }

}

data class VerifyEmailParams(
    val userId: String
)