package dev.december.jeterbackend.supplier.features.chats.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.chats.domain.services.ChatService
import org.springframework.stereotype.Component

@Component
class DeleteChatUseCase(
    private val chatService: ChatService
): UseCase<DeleteChatParams, Unit> {
    override suspend fun invoke(params: DeleteChatParams): Data<Unit> {
        return chatService.deleteChat(params.chatId)
    }
}

data class DeleteChatParams(
    val chatId: String
)