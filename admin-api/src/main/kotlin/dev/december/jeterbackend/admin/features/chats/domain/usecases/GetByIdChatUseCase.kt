package dev.december.jeterbackend.admin.features.chats.domain.usecases

import dev.december.jeterbackend.admin.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatSupport
import org.springframework.stereotype.Component

@Component
class GetByIdChatUseCase(
    private val chatService: ChatService
): UseCase<GetByIdChatParams, ChatSupport> {
    override suspend fun invoke(params: GetByIdChatParams): Data<ChatSupport> {
        return chatService.getById(params.chatId)
    }
}

data class GetByIdChatParams(
    val chatId: String
)