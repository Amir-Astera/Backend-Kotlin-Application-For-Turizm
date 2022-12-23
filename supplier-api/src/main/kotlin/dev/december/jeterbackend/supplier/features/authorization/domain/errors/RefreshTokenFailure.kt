package dev.december.jeterbackend.supplier.features.authorization.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure


data class RefreshTokenFailure(
    override val code: Int = 400,
    override val message: String = "Refresh token failure!"
) : Failure