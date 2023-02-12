package dev.december.jeterbackend.supplier.features.chats.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.SupportMessage
import dev.december.jeterbackend.supplier.features.chats.domain.services.ChatService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class GetSupportChatUseCase(
    private val chatService: ChatService
): UseCase<GetSupportChatParams, Page<SupportMessage>> {
    override suspend fun invoke(params: GetSupportChatParams): Data<Page<SupportMessage>> {
        return chatService.getSupport(params.supplierId, params.page, params.size, params.searchField)
    }
}

data class GetSupportChatParams(
    val supplierId: String,
    val page: Int,
    val size: Int,
    val searchField: String?
)