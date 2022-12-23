package dev.december.jeterbackend.supplier.features.chats.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ChatGetAllMessageException(
    override val code: Int = 500,
    override val message: String = "Can not get messages at this moment"
): Failure
