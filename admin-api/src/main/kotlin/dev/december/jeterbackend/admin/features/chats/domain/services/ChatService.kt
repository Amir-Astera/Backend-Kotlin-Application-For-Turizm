package dev.december.jeterbackend.admin.features.chats.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatSupport
import org.springframework.data.domain.Page
import java.time.LocalDateTime

interface ChatService {
    suspend fun getAll(
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Page<ChatSupport>>
    suspend fun getById(chatId: String): Data<ChatSupport>
    suspend fun getSupplier(supplierId: String): Data<ChatSupport>
    suspend fun getClient(clientId: String): Data<ChatSupport>
    suspend fun deleteList(ids: List<String>): Data<Unit>
}