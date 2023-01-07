package dev.december.jeterbackend.client.features.suppliers.domain.usecases

import dev.december.jeterbackend.client.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.calendar.domain.models.Calendar
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class GetSupplierCalendarUseCase (
    private val supplierService: SupplierService
) : UseCase<GetSupplierCalendarParams, Calendar> {
    override suspend fun invoke(params: GetSupplierCalendarParams): Data<Calendar> {
        return supplierService.getCalendar(params.supplierId, params.firstDayOfMonth)
    }
}

data class GetSupplierCalendarParams(
    val supplierId: String,
    val firstDayOfMonth: LocalDate,
)