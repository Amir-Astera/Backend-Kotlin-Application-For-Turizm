package dev.december.jeterbackend.client.features.chats.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ChatGetArchivedFailure(
    override val code: Int = 404,
    override val message: String = "Archived chat not found!",
) : Failure