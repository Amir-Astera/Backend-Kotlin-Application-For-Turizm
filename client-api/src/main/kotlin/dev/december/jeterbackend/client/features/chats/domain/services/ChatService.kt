package dev.december.jeterbackend.client.features.chats.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.Chat
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatArchiveStatus
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatMessage
import dev.december.jeterbackend.shared.features.chats.domain.models.Message
import org.springframework.data.domain.Page
import java.time.LocalDateTime

interface ChatService {
    suspend fun getAll(page: Int,
                       size: Int,
                       searchField: String?,
                       createdFrom: LocalDateTime?,
                       createdTo: LocalDateTime?,
                       status: ChatArchiveStatus?,
                       userId: String
    ): Data<String>//Page<ChatMessage>
    suspend fun getById(chatId: String): Data<String>//<Chat>
    suspend fun getSupplier(userId: String, supplierId: String): Data<Unit>//<Chat>
    suspend fun archiveChat(chatId: String): Data<Unit>
    suspend fun unarchiveChat(chatId: String): Data<Unit>
    suspend fun deleteChat(chatId: String): Data<Unit>
    suspend fun getAllMessages(chatId: String, page: Int, size: Int, searchField: String?): Data<Unit>//<Page<Message>>
}