package dev.december.jeterbackend.shared.features.user.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class UserInvalidIdentityFailure(
    override val code: Int = 400,
    override val message: String = "At least email or phone must be filled!"
) : Failure
