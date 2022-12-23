package dev.december.jeterbackend.shared.features.orders.domain.models

import dev.december.jeterbackend.shared.features.orders.domain.models.Model
import dev.december.jeterbackend.shared.features.orders.domain.models.ResponseStatus

data class Order(
    val model: Model,
    val status: ResponseStatus,
    val success: Boolean,
    val message: String? = null
)
