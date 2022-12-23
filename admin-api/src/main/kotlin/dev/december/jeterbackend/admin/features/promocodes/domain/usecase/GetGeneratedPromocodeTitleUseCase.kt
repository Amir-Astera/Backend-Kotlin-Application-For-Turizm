package dev.december.jeterbackend.admin.features.promocodes.domain.usecase

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import org.springframework.stereotype.Component

@Component
class GetGeneratedPromocodeTitleUseCase(
    private val promocodeService: PromocodeService,
) : UseCase<Unit, String> {
    override suspend fun invoke(params: Unit): Data<String> {
        return promocodeService.generateTitle()
    }
}