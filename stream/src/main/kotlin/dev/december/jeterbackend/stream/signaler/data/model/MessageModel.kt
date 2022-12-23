package dev.december.jeterbackend.stream.signaler.data.model

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole

data class MessageModel(
    val chatId: String,
    val userId: String,
    val message: String,
    val platformRole: PlatformRole
)
