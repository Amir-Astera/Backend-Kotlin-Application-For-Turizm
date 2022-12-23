package dev.december.jeterbackend.client.features.promocodes.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.models.Promocode
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import org.springframework.stereotype.Component

@Component
class GetDisocuntUseCase(
   val promocodeService: PromocodeService
) : UseCase<GetDiscountParams, Promocode> {
    override suspend fun invoke(params: GetDiscountParams): Data<Promocode> {
        return promocodeService.validatePromocode(
            params.code,
            params.supplierId,
            params.userId,
        )
    }
}

data class GetDiscountParams(
    val code: String,
    val supplierId: String,
    val userId: String,
)