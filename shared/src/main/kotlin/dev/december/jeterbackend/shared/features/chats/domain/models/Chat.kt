package dev.december.jeterbackend.shared.features.chats.domain.models

import dev.december.jeterbackend.shared.features.chats.domain.models.ChatArchiveStatus
import dev.december.jeterbackend.shared.features.chats.domain.models.Message
import java.time.LocalDateTime

data class Chat(
    val chatId: String,
//    val client: Client,
//    val supplier: Supplier,
    val archiveStatus: ChatArchiveStatus,
    val messages: Set<Message>?,
    val unreadMessagesCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
