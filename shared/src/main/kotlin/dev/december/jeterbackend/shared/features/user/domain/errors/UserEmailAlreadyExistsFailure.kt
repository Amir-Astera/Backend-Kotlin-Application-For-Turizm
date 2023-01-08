package dev.december.jeterbackend.shared.features.user.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class UserEmailAlreadyExistsFailure(
    val email: String,
    override val code: Int = 409,
    override val message: String = "User with email $email already exists!",
) : Failure

