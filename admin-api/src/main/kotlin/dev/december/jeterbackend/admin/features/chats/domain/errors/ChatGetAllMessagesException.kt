package dev.december.jeterbackend.admin.features.chats.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ChatGetAllMessagesException(
    override val code: Int = 500,
    override val message: String = "Can not get messages at this moment"

): Failure
