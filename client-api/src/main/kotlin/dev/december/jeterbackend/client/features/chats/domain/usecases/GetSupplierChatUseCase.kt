package dev.december.jeterbackend.client.features.chats.domain.usecases

import dev.december.jeterbackend.client.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.Chat
import org.springframework.stereotype.Component

@Component
class GetSupplierChatUseCase(
    private val chatService: ChatService
): UseCase<GetSupplierChatParams, Chat> {
    override suspend fun invoke(params: GetSupplierChatParams): Data<Chat>{
        return chatService.getSupplier(params.userId, params.supplierId)
    }
}

data class GetSupplierChatParams(
    val userId: String,
    val supplierId: String
)