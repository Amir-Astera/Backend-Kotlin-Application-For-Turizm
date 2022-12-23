package dev.december.jeterbackend.shared.features.promocodes.presentation.dto

import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeDiscountType
import java.time.LocalDateTime

data class CreatePromocodeData(
    val code: String,
    val description: String?,
    val discountType: PromocodeDiscountType,
    val discountAmount: Int?,
    val discountPercentage: Double?,
    val activationLimit: Int,
    val validityFrom: LocalDateTime,
    val validityTo: LocalDateTime,
)
