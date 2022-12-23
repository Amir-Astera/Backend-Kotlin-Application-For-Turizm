package dev.december.jeterbackend.client.features.promocodes.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeDiscountAmount
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import org.springframework.stereotype.Component

@Component
class GetFinalPriceUseCase(
   val promocodeService: PromocodeService
) : UseCase<GetFinalPriceParams, PromocodeDiscountAmount> {
    override suspend fun invoke(params: GetFinalPriceParams): Data<PromocodeDiscountAmount> {
        return promocodeService.validatePromocode(
            params.code,
            params.supplierId,
            params.userId,
        ).fold { promocode -> promocodeService.getFinalPrice(promocode, params.price) }
    }
}

data class GetFinalPriceParams(
    val code: String,
    val supplierId: String,
    val userId: String,
    val price: Double,
)