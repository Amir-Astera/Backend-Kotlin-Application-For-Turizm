package dev.december.jeterbackend.shared.features.user.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class UserNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "User not found!"
) : Failure
