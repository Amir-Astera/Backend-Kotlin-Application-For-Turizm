package dev.december.jeterbackend.client.features.chats.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ChatDeleteException(
    override val code: Int = 500,
    override val message: String = "Cannot delete chat at this moment!",
) : Failure
