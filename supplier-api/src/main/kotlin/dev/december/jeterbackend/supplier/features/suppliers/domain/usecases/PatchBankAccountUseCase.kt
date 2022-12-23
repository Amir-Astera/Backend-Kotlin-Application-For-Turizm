package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierBankAccount
import org.springframework.stereotype.Component

@Component
class PatchBankAccountUseCase(
    private val supplierService: SupplierService
) : UseCase<PatchBankAccountParams, String> {
    override suspend fun invoke(params: PatchBankAccountParams): Data<String> {
        return supplierService.updateBankAccount(
            params.userId,
            params.bankAccount
        )
    }
}

data class PatchBankAccountParams(
    val userId: String,
    val bankAccount: SupplierBankAccount
)