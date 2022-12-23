package dev.december.jeterbackend.admin.features.chats.domain.usecases

import dev.december.jeterbackend.admin.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class DeleteChatListUseCase(
    private val chatService: ChatService
): UseCase<DeleteListChatParams, Unit> {
    override suspend fun invoke(params: DeleteListChatParams): Data<Unit> {
        return chatService.deleteList(params.ids)
    }

}

data class DeleteListChatParams(
    val ids: List<String>
)