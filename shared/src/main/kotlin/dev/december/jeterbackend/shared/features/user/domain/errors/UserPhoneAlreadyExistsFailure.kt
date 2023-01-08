package dev.december.jeterbackend.shared.features.user.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class UserPhoneAlreadyExistsFailure(
    val phone: String,
    override val code: Int = 409,
    override val message: String = "User with phone number $phone already exists!"
) : Failure

