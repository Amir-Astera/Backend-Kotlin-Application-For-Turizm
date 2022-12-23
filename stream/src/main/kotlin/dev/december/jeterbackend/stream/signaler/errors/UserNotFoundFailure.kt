package dev.december.jeterbackend.stream.signaler.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class UserNotFoundFailure(
    override val message: String = "Not found user!",
    override val code: Int = 500
): Failure
