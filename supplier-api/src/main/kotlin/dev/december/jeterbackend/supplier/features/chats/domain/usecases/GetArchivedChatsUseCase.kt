package dev.december.jeterbackend.supplier.features.chats.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatSupplier
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class GetArchivedChatsUseCase(
    private val chatService: ChatService
): UseCase<GetArchivedChatsParams, Page<ChatSupplier>> {
    override suspend fun invoke(params: GetArchivedChatsParams): Data<Page<ChatSupplier>> {
        return chatService.getArchivedChats(params.page, params.size, params.supplierId)
    }
}

data class GetArchivedChatsParams(
    val page: Int,
    val size: Int,
    val supplierId: String
)