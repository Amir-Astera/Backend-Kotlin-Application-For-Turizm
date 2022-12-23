package dev.december.jeterbackend.stream.signaler.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ActionNotAllowedFailure(
    override val code: Int = 400,
    override val message: String = "Action not allowed!",
): Failure