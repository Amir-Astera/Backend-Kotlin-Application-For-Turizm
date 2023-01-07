package dev.december.jeterbackend.client.features.suppliers.domain.usecases

import dev.december.jeterbackend.client.features.suppliers.presentation.dto.FreeTimeDto
import dev.december.jeterbackend.client.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class GetSupplierFreeTimeUseCase (
    private val supplierService: SupplierService
) : UseCase<GetSupplierFreeTimeParams, List<FreeTimeDto>> {
    override suspend fun invoke(params: GetSupplierFreeTimeParams): Data<List<FreeTimeDto>> {
        return supplierService.getSupplierFreeTime(params.supplierId, params.date)
    }
}

data class GetSupplierFreeTimeParams(
    val supplierId: String,
    val date: LocalDate
)