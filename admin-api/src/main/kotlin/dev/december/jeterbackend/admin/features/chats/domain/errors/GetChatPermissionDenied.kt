package dev.december.jeterbackend.admin.features.chats.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class GetChatPermissionDenied(
    override val code: Int = 403,
    override val message: String = "Permission denied!"
): Failure