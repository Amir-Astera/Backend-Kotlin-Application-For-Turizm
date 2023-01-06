package dev.december.jeterbackend.shared.features.chats.domain.models

import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import java.time.LocalDateTime

data class Message(
    val id: String,
    val chatId: String,
    val client: Client?,
    val supplier: Supplier?,
    val content: String,
    val status: MessageStatus,
    val files: List<File>,
    val createdAt: LocalDateTime,
)
