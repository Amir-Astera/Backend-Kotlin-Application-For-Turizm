package dev.december.jeterbackend.shared.features.promocodes.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PromocodeClientAlreadyActivatedFailure(
    override val code: Int = 500,
    override val message: String = "Promocode has already been activated by this client!"
) : Failure