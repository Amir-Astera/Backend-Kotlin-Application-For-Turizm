package dev.december.jeterbackend.supplier.features.authorization.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure


data class ResetPasswordEmailFailure(
    override val code: Int = 500,
    override val message: String = "Cannot send password reset email!"
): Failure