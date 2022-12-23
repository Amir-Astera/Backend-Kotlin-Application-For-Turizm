package dev.december.jeterbackend.client.features.promocodes.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import org.springframework.stereotype.Component

@Component
class ApplyPromocodeUseCase(
   val promocodeService: PromocodeService
) : UseCase<ApplyPromocodeParams, Unit> {
    override suspend fun invoke(params: ApplyPromocodeParams): Data<Unit> {
        return promocodeService.applyPromocode(
            params.promocodeId,
            params.userId,
        )
    }
}

data class ApplyPromocodeParams(
    val promocodeId: String,
    val userId: String,
)