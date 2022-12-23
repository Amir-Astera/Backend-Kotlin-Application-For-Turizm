package dev.december.jeterbackend.supplier.features.chats.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ChatGetFailure(
    override val code: Int = 404,
    override val message: String = "Chat not found!"
): Failure