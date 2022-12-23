package dev.december.jeterbackend.shared.features.promocodes.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PromocodeNotSuppliersFailure(
    val title: String,
    override val code: Int = 500,
    override val message: String = "Promocode $title doesnt belong to you/supplier!"
) : Failure