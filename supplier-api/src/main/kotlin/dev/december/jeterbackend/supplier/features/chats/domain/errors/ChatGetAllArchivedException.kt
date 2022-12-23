package dev.december.jeterbackend.supplier.features.chats.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ChatGetAllArchivedException(
    override val code: Int = 500,
    override val message: String = "Cannot get archived chats!"
): Failure