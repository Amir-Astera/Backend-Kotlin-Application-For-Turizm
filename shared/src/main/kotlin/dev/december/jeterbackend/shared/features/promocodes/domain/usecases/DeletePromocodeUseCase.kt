package dev.december.jeterbackend.shared.features.promocodes.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import org.springframework.stereotype.Component

@Component
class DeletePromocodeUseCase(
    private val promocodeService: PromocodeService,
) : UseCase<DeletePromocodeParams, Unit> {
    override suspend fun invoke(params: DeletePromocodeParams): Data<Unit> {
        return promocodeService.delete(
            params.id,
            params.userId
        )
    }
}

data class DeletePromocodeParams(
    val id: String,
    val userId: String? = null,
)