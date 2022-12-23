package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component

@Component
class UpdateRegistrationTokenUseCase (
    private val supplierService: SupplierService
) : UseCase<UpdateRegistrationTokenParams, Unit> {
    override suspend fun invoke(params: UpdateRegistrationTokenParams): Data<Unit> {
        return supplierService.updateRegistrationToken(
            params.userId,
            params.registrationToken
        )
    }
}

data class UpdateRegistrationTokenParams(
    val userId: String,
    val registrationToken: String,
)