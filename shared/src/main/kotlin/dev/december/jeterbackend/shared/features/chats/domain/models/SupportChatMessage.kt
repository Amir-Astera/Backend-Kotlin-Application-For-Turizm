package dev.december.jeterbackend.shared.features.chats.domain.models

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import java.time.LocalDateTime

data class SupportChatMessage(
    val chatId: String,
    val supplier: Supplier?,
    val client: Client?,
    val lastMessage: Message? = null,
    val authority: PlatformRole,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
