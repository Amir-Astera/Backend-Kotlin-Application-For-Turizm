package dev.december.jeterbackend.shared.features.chats.domain.models

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import java.time.LocalDateTime

data class ChatSupport(
    val chatId: String,
//    val supplierId: Supplier?,
//    val clientId: Client?,
    val authority: PlatformRole,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
