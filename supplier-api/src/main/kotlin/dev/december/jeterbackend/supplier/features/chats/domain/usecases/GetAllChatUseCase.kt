package dev.december.jeterbackend.supplier.features.chats.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatSupplier
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetAllChatUseCase(
    private val chatService: ChatService
): UseCase<GetAllChatParams, Page<ChatSupplier>>{
    override suspend fun invoke(params: GetAllChatParams): Data<Page<ChatSupplier>> {
        return chatService.getAll(params.page, params.size, params.searchField, params.createdFrom, params.createdTo, params.supplierId)
    }
}

data class GetAllChatParams(
    val page: Int,
    val size: Int,
    val searchField: String?,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?,
    val supplierId: String
)