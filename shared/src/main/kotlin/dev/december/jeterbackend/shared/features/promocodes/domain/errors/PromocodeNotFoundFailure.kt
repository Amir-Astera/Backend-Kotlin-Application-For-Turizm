package dev.december.jeterbackend.shared.features.promocodes.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PromocodeNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Promocode not found!"
) : Failure
