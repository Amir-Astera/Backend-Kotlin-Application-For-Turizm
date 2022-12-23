package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class UpdateSupplierActivityStatusUseCase(
    private val supplierService: SupplierService
): UseCase<UpdateSupplierActivityStatusParams, Unit> {
    override suspend fun invoke(params: UpdateSupplierActivityStatusParams): Data<Unit> {
        return supplierService.updateActivityStatus(
            params.userId,
            params.activityStatus,
            params.firstDayOfMonth
        )
    }
}

data class UpdateSupplierActivityStatusParams(
    val userId: String,
    val activityStatus: AccountActivityStatus,
    val firstDayOfMonth: LocalDate
)