package dev.december.jeterbackend.client.features.chats.domain.usecases

import dev.december.jeterbackend.client.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.files.domain.models.File
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class GetChatMediaFilesUseCase(
    private val chatService: ChatService
): UseCase<GetChatMediaFilesParams, Unit> {//Page<File>
    override suspend fun invoke(params: GetChatMediaFilesParams): Data<Unit> {//Page<File>
        return chatService.getAllMediaFiles(
            params.userId, params.chatId, params.page, params.size
        )
    }
}

data class GetChatMediaFilesParams(
    val userId: String,
    val chatId: String,
    val page: Int,
    val size: Int,
)