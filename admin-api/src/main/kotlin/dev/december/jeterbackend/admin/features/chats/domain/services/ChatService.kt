package dev.december.jeterbackend.admin.features.chats.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.SupportChat
import dev.december.jeterbackend.shared.features.chats.domain.models.SupportChatMessage
import dev.december.jeterbackend.shared.features.chats.domain.models.SupportMessage
import dev.december.jeterbackend.shared.features.files.domain.models.File
import org.springframework.data.domain.Page
import java.time.LocalDateTime

interface ChatService {
    suspend fun getAll(
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Page<SupportChatMessage>>
    suspend fun getById(chatId: String): Data<SupportChat>
    suspend fun deleteList(ids: List<String>): Data<Unit>
    suspend fun getAllMessages(chatId: String, page: Int, size: Int, searchField: String?): Data<Page<SupportMessage>>
    suspend fun getAllMediaFiles(userId: String, chatId: String, page: Int, size: Int): Data<Page<File>>
}