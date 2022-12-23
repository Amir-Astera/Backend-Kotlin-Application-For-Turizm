package dev.december.jeterbackend.client.features.authorization.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ResetPasswordFailure(
    override val code: Int = 500,
    override val message: String = "Cannot reset password!"
): Failure