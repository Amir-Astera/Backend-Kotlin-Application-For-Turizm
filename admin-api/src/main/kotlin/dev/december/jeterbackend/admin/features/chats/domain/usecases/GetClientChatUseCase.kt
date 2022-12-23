package dev.december.jeterbackend.admin.features.chats.domain.usecases

import dev.december.jeterbackend.admin.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatSupport
import org.springframework.stereotype.Component

@Component
class GetClientChatUseCase(
    private val chatService: ChatService
): UseCase<GetClientChatParams, ChatSupport> {
    override suspend fun invoke(params: GetClientChatParams): Data<ChatSupport> {
        return chatService.getClient(params.clientId)
    }
}

data class GetClientChatParams(
    val clientId: String
)