package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.supplier.features.suppliers.presentation.dto.UpdateSupplierCalendar
import org.springframework.stereotype.Component

@Component
class UpdateSupplierCalendarUseCase (
    private val supplierService: SupplierService
) : UseCase<UpdateSupplierCalendarParams, Unit> {
    override suspend fun invoke(params: UpdateSupplierCalendarParams): Data<Unit> {
        return supplierService.updateCalendar(
            params.userId,
            params.supplierCalendar.firstDayOfMonth,
            params.supplierCalendar.workingDays,
        )
    }
}

data class UpdateSupplierCalendarParams(
    val userId: String,
    val supplierCalendar: UpdateSupplierCalendar,
)