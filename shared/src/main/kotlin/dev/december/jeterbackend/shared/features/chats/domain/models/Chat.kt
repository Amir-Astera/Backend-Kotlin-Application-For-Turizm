package dev.december.jeterbackend.shared.features.chats.domain.models

import dev.december.jeterbackend.shared.features.chats.domain.models.ChatArchiveStatus
import dev.december.jeterbackend.shared.features.chats.domain.models.Message
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import java.time.LocalDateTime

data class Chat(
    val chatId: String,
    val archiveStatus: ChatArchiveStatus,
    val client: Client,
    val supplier: Supplier,
    val messages: Set<Message>?,
    val clientUnreadMessagesCount: Int,
    val supplierUnreadMessagesCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
