package dev.december.jeterbackend.supplier.features.chats.domain.services


import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.*
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
    ): Data<Unit>//Page<ChatMessage>
//    suspend fun getById(chatId: String): Data<Chat>
    suspend fun getClient(userId: String, clientId: String): Data<Unit>//Chat
    suspend fun archiveChat(chatId: String): Data<Unit>
    suspend fun unarchiveChat(chatId: String): Data<Unit>
    suspend fun deleteChat(chatId: String): Data<Unit>
    suspend fun getAllMessages(chatId: String, page: Int, size: Int, searchField: String?): Data<Unit>//Page<Message>
}