package dev.december.jeterbackend.shared.features.promocodes.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeDiscountType
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CreatePromocodeUseCase(
    private val promocodeService: PromocodeService
) : UseCase<CreatePromocodeParams, String> {
    override suspend fun invoke(params: CreatePromocodeParams): Data<String> {
        return promocodeService.create(
            params.code,
            params.description,
            params.discountType,
            params.discountAmount,
            params.discountPercentage,
            params.activationLimit,
            params.validityFrom,
            params.validityTo,
            params.adminId,
            params.userId,
        )
    }
}

data class CreatePromocodeParams(
    val code: String,
    val description: String?,
    val discountType: PromocodeDiscountType,
    val discountAmount: Int?,
    val discountPercentage: Double?,
    val activationLimit: Int,
    val validityFrom: LocalDateTime,
    val validityTo: LocalDateTime,
    val adminId: String? = null,
    val userId: String? = null,
)