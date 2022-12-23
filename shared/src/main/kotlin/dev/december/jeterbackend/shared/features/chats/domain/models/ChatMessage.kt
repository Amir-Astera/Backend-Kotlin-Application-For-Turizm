package dev.december.jeterbackend.shared.features.chats.domain.models

import java.time.LocalDateTime

data class ChatMessage(
    val chatId: String,
//    val client: Client,
//    val supplier: Supplier,
    val archiveStatus: ChatArchiveStatus,
    val lastMessage: Message? = null,
    val unreadMessagesCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
