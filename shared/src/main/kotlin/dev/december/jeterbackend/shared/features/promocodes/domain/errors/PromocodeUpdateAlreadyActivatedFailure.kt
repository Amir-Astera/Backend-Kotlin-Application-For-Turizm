package dev.december.jeterbackend.shared.features.promocodes.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PromocodeUpdateAlreadyActivatedFailure(
    override val code: Int = 500,
    override val message: String = "Promocode has already been activated at least once, can not update!"
) : Failure
