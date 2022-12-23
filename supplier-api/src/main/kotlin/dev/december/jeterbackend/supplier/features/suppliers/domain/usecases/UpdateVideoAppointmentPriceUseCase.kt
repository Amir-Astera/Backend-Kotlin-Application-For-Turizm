package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class UpdateVideoAppointmentPriceUseCase (
    private val supplierService: SupplierService
) : UseCase<UpdateVideoAppointmentPriceParams, Unit> {
    override suspend fun invoke(params: UpdateVideoAppointmentPriceParams): Data<Unit> {
        return supplierService.updateVideoPrice(
            params.userId,
            params.price
        )
    }
}

data class UpdateVideoAppointmentPriceParams(
    val userId: String,
    val price: Int?
)