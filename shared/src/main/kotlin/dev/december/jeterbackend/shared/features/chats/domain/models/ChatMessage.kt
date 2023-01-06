package dev.december.jeterbackend.shared.features.chats.domain.models

import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import java.time.LocalDateTime

data class ChatMessage(
    val chatId: String,
    val client: Client,
    val supplier: Supplier,
    val archiveStatus: ChatArchiveStatus,
    val lastMessage: Message? = null,
    val clientUnreadMessagesCount: Int,
    val supplierUnreadMessagesCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)