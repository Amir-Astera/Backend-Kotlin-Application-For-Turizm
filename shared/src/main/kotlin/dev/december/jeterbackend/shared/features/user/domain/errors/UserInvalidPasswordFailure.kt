package dev.december.jeterbackend.shared.features.user.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class UserInvalidPasswordFailure(
    override val code: Int = 400,
    override val message: String = "Incorrect password!"
) : Failure
