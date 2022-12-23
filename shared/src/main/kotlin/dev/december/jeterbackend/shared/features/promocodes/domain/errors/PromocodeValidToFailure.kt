package dev.december.jeterbackend.shared.features.promocodes.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PromocodeValidToFailure(
    override val code: Int = 409,
    override val message: String = "Expiration date can not be earlier than today!"
) : Failure
