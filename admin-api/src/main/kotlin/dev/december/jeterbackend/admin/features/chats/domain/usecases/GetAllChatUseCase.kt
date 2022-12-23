package dev.december.jeterbackend.admin.features.chats.domain.usecases

import dev.december.jeterbackend.admin.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatSupport
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
class GetAllChatUseCase (
    private val chatService: ChatService
): UseCase<GetAllChatParams, Page<ChatSupport>> {
    override suspend fun invoke(params: GetAllChatParams): Data<Page<ChatSupport>> {
        return chatService.getAll(params.page, params.size, params.createdFrom, params.createdTo)
    }
}

data class GetAllChatParams(
    val page: Int,
    val size: Int,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?
)