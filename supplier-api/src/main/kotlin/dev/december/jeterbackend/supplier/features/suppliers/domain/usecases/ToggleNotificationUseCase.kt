package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component

@Component
class ToggleNotificationUseCase(
    private val supplierService: SupplierService
): UseCase<ToggleNotificationParams, Unit> {
    override suspend fun invoke(params: ToggleNotificationParams): Data<Unit> {
        return supplierService.toggleNotification(params.userId, params.toggleStatus)
    }
}

data class ToggleNotificationParams(
    val userId: String,
    val toggleStatus: Boolean
)