package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component

@Component
class UpdateAudioAppointmentPriceUseCase (
    private val supplierService: SupplierService
) : UseCase<UpdateAudioAppointmentPriceParams, Unit> {
    override suspend fun invoke(params: UpdateAudioAppointmentPriceParams): Data<Unit> {
        return supplierService.updateAudioPrice(
            params.userId,
            params.price
        )
    }
}

data class UpdateAudioAppointmentPriceParams(
    val userId: String,
    val price: Int?
)