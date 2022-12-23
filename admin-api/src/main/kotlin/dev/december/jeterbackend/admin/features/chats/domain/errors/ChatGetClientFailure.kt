package dev.december.jeterbackend.admin.features.chats.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ChatGetClientFailure(
    override val code: Int = 500,
    override val message: String = "Cannot get chat client at this moment!"
): Failure