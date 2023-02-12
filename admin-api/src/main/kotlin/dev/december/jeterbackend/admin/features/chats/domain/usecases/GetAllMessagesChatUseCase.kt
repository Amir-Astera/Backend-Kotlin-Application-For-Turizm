package dev.december.jeterbackend.admin.features.chats.domain.usecases

import dev.december.jeterbackend.admin.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.SupportMessage
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class GetAllMessagesUseCase(
    private val chatService: ChatService
): UseCase<GetAllMessagesChatParams, Page<SupportMessage>> {
    override suspend fun invoke(params: GetAllMessagesChatParams): Data<Page<SupportMessage>> {
        return chatService.getAllMessages(params.id, params.page, params.size, params.searchField)
    }
}

data class GetAllMessagesChatParams(
    val id: String,
    val page: Int,
    val size: Int,
    val searchField: String?
)