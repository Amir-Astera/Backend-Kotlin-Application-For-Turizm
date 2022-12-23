package dev.december.jeterbackend.stream.signaler.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class MessageSaveFailure(
    override val code: Int = 500,
    override val message: String = "Could not save message!"
): Failure
