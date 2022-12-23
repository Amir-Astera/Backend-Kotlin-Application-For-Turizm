package dev.december.jeterbackend.shared.features.promocodes.domain.models

data class PromocodeDiscountAmount(
    val discountAmount: Double,
    val finalPrice: Double,
    val promocode: Promocode,
)