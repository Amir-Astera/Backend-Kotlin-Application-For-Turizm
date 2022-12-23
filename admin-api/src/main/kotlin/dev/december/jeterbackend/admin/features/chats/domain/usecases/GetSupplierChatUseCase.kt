package dev.december.jeterbackend.admin.features.chats.domain.usecases

import dev.december.jeterbackend.admin.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatSupport
import org.springframework.stereotype.Component

@Component
class GetSupplierChatUseCase(
    private val chatService: ChatService
): UseCase<GetSupplierChatParams, ChatSupport> {
    override suspend fun invoke(params: GetSupplierChatParams): Data<ChatSupport> {
        return chatService.getSupplier(params.supplierId)
    }
}

data class GetSupplierChatParams(
    val supplierId: String
)