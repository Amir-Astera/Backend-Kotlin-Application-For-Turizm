package dev.december.jeterbackend.shared.features.promocodes.domain.models

import dev.december.jeterbackend.shared.features.admin.domain.models.Admin
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import java.time.LocalDateTime

data class Promocode(
    val id: String,
    val code: String,
    val description: String?,
    val discountType: PromocodeDiscountType,
    val discountAmount: Int?,
    val discountPercentage: Double?,
    val validityFrom: LocalDateTime,
    val validityTo: LocalDateTime,
    val status: PromocodeStatus,
    val activationLimit: Int,
    val activatedAmount: Int,
    val totalAttempts: Int,
    val admin: Admin?,
    val supplier: Supplier?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
