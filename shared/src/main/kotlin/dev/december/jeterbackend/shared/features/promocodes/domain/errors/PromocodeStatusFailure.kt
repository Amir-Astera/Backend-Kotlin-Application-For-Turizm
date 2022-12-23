package dev.december.jeterbackend.shared.features.promocodes.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PromocodeStatusFailure(
    override val code: Int = 500,
    override val message: String = "Promocode's not Active failure!"
) : Failure