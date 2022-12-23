package dev.december.jeterbackend.client.features.chats.domain.usecases

import dev.december.jeterbackend.client.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.Chat
import org.springframework.stereotype.Component

@Component
class GetChatUseCase(
    private val chatService: ChatService
): UseCase<GetChatParams, Chat> {
    override suspend fun invoke(params: GetChatParams): Data<Chat> {
        return chatService.getById(params.chatId)
    }
}

data class GetChatParams(
    val chatId: String
)