package dev.december.jeterbackend.supplier.features.chats.domain.usecases

import dev.december.jeterbackend.supplier.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class UnArchiveChatUseCase(
    private val chatService: ChatService
): UseCase<UnArchiveChatParams, Unit> {
    override suspend fun invoke(params: UnArchiveChatParams): Data<Unit> {
        return chatService.unarchiveChat(params.chatId)
    }
}

data class UnArchiveChatParams(
    val chatId: String
)