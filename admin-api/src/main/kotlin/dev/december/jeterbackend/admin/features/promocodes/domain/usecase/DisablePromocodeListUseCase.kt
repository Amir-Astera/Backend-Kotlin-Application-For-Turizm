package dev.december.jeterbackend.admin.features.promocodes.domain.usecase

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import org.springframework.stereotype.Component

@Component
class DisablePromocodeListUseCase(
    private val promocodeService: PromocodeService,
) : UseCase<DisablePromocodeListParams, Unit> {
    override suspend fun invoke(params: DisablePromocodeListParams): Data<Unit> {
        return promocodeService.disableList(params.ids)
    }
}

data class DisablePromocodeListParams(
    val ids: List<String>,
)