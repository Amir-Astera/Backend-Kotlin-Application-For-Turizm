package dev.december.jeterbackend.shared.features.promocodes.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PromocodeTitleAlreadyExistsFailure(
    val title: String,
    override val code: Int = 409,
    override val message: String = "Promocode with title $title already exists!",
) : Failure
