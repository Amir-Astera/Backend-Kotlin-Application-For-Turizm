package dev.december.jeterbackend.shared.features.promocodes.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PromocodeValidationFailure(
    override val code: Int = 409,
    override val message: String = "Promocode validation!"
) : Failure