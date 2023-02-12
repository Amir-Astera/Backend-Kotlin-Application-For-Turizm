package dev.december.jeterbackend.shared.features.chats.domain.models

import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import java.time.LocalDateTime

data class SupportChat(
    val chatId: String,
    val client: Client?,
    val supplier: Supplier?,
    val messages: Set<Message>?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
