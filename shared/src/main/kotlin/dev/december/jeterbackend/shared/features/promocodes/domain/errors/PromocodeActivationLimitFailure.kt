package dev.december.jeterbackend.shared.features.promocodes.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PromocodeActivationLimitFailure(
    override val code: Int = 500,
    override val message: String = "Promocode activation limit can not be less or equal to 0!"
) : Failure