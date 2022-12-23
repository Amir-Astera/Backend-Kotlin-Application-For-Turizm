package dev.december.jeterbackend.supplier.features.chats.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.chats.domain.services.ChatService
import org.springframework.stereotype.Component

@Component
class ArchiveChatUseCase(
    private val chatService: ChatService
): UseCase<ArchiveChatParams, Unit> {
    override suspend fun invoke(params: ArchiveChatParams): Data<Unit> {
        return chatService.archiveChat(params.chatId)
    }
}

data class ArchiveChatParams(
    val chatId: String
)