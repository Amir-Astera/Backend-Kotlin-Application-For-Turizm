package dev.december.jeterbackend.shared.features.promocodes.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PromocodeCreateFailure(
    override val code: Int = 500,
    override val message: String = "Cannot create promocode!"
) : Failure