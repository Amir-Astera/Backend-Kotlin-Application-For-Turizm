package dev.december.jeterbackend.stream.signaler.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class MessageSendFailure(
    override val code: Int = 500,
    override val message: String = "Cannot send message!",
) : Failure