package dev.december.jeterbackend.shared.features.chats.domain.models

import java.time.LocalDateTime

data class ChatSupplier(
    val chatId: String,
//    val supplier: Supplier,
//    val client: Client,
    val archiveStatus: ChatArchiveStatus,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
