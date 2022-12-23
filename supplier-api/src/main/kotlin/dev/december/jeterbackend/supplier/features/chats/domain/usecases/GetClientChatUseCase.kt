package dev.december.jeterbackend.supplier.features.chats.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatSupplier
import org.springframework.stereotype.Component

@Component
class GetClientChatUseCase(
    private val chatService: ChatService
): UseCase<GetClientChatParams, ChatSupplier> {
    override suspend fun invoke(params: GetClientChatParams): Data<ChatSupplier>{
        return chatService.getClient(params.supplierId, params.clientId)
    }
}

data class GetClientChatParams(
    val supplierId: String,
    val clientId: String
)