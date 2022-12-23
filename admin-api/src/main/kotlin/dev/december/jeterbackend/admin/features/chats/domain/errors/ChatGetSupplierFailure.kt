package dev.december.jeterbackend.admin.features.chats.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ChatGetSupplierFailure(
    override val code: Int = 500,
    override val message: String = "Cannot get chat supplier at this moment!"
): Failure
