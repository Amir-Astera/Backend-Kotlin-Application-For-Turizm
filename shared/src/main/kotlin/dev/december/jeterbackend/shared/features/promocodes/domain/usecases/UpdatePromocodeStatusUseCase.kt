package dev.december.jeterbackend.shared.features.promocodes.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeStatus
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import org.springframework.stereotype.Component

@Component
class UpdatePromocodeStatusUseCase(
    private val promocodeService: PromocodeService,
) : UseCase<UpdatePromocodeActivityStatusParams, Unit> {
    override suspend fun invoke(params: UpdatePromocodeActivityStatusParams): Data<Unit> {
        return promocodeService.updateStatus(
            params.id,
            params.userId,
            params.status,
        )
    }
}

data class UpdatePromocodeActivityStatusParams(
    val id: String,
    val status: PromocodeStatus,
    val userId: String? = null,
)