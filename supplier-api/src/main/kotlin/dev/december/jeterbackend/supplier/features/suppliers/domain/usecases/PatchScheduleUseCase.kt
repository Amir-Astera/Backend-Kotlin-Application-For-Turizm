package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierSchedule
import org.springframework.stereotype.Component

@Component
class PatchScheduleUseCase(
    private val supplierService: SupplierService
): UseCase<PatchScheduleParams, String> {
    override suspend fun invoke(params: PatchScheduleParams): Data<String> {
        return supplierService.updateSchedule(
            params.userId,
            params.schedule
        )
    }
}

data class PatchScheduleParams(
    val userId: String,
    val schedule: SupplierSchedule
)