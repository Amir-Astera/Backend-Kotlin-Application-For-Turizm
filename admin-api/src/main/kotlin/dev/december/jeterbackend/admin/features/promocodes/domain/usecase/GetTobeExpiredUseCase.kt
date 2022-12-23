package dev.december.jeterbackend.admin.features.promocodes.domain.usecase

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import org.springframework.stereotype.Component

@Component
class GetTobeExpiredUseCase(
    private val promocodeService: PromocodeService,
) : UseCase<Unit, Unit> {
    override suspend fun invoke(params: Unit): Data<Unit> {
        return promocodeService.expirationJob()
    }
}