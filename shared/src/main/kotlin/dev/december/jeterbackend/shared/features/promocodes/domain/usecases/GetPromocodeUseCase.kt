package dev.december.jeterbackend.shared.features.promocodes.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.models.Promocode
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import org.springframework.stereotype.Component

@Component
class GetPromocodeUseCase(
    private val promocodeService: PromocodeService,
) : UseCase<GetPromocodeParams, Promocode> {
    override suspend fun invoke(params: GetPromocodeParams): Data<Promocode> {
        return promocodeService.get(params.id, params.userId)
    }
}

data class GetPromocodeParams(
    val id: String,
    val userId: String? = null
)