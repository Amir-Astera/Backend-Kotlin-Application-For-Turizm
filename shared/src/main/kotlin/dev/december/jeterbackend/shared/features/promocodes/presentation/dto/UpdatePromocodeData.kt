package dev.december.jeterbackend.shared.features.promocodes.presentation.dto

import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeDiscountType
import java.time.LocalDateTime

data class UpdatePromocodeData(
    val code: String?,
    val description: String?,
    val discountType: PromocodeDiscountType?,
    val discountAmount: Int?,
    val discountPercentage: Double?,
    val validityFrom: LocalDateTime?,
    val validityTo: LocalDateTime?,
    val activationLimit: Int?,
)
