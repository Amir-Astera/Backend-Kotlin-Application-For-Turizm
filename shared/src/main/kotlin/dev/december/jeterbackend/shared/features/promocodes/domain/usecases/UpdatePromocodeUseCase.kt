package dev.december.jeterbackend.shared.features.promocodes.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeDiscountType
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UpdatePromocodeUseCase(
    private val promocodeService: PromocodeService
) : UseCase<UpdatePromocodeParams, Unit> {
    override suspend fun invoke(params: UpdatePromocodeParams): Data<Unit> {
        return promocodeService.update(
            params.promoId,
            params.adminId,
            params.userId,
            params.code,
            params.description,
            params.discountType,
            params.discountAmount,
            params.discountPercentage,
            params.activationLimit,
            params.validityFrom,
            params.validityTo,
            params.updatedAt,
        )
    }
}

data class UpdatePromocodeParams(
    val promoId: String,
    val adminId: String? = null,
    val userId: String? = null,
    val code: String?,
    val description: String?,
    val discountType: PromocodeDiscountType?,
    val discountAmount: Int?,
    val activationLimit: Int?,
    val discountPercentage: Double?,
    val validityFrom: LocalDateTime?,
    val validityTo: LocalDateTime?,
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)