package dev.december.jeterbackend.supplier.features.chats.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatSupplier
import org.springframework.stereotype.Component

@Component
class GetArchivedChatUseCase(
    private val chatService: ChatService
): UseCase<GetArchivedChatParams, ChatSupplier> {
    override suspend fun invoke(params: GetArchivedChatParams): Data<ChatSupplier> {
        return chatService.getArchivedChat(params.chatId)
    }
}

data class GetArchivedChatParams(
    val chatId: String
)