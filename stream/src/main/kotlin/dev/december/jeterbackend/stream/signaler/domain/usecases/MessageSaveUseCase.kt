package dev.december.jeterbackend.stream.signaler.domain.usecases

import dev.december.jeterbackend.stream.core.usecases.UseCase
import dev.december.jeterbackend.stream.signaler.data.model.ResponseMessageModel
import dev.december.jeterbackend.stream.signaler.domain.services.MessageService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.Message
import dev.december.jeterbackend.shared.features.files.domain.models.File
import org.springframework.stereotype.Component

@Component
class MessageSaveUseCase(
    private val messageService: MessageService
): UseCase<Message, MessageSaveParams> {
    override fun invoke(param: MessageSaveParams): Data<Message> {
        return messageService.save(
            param.chatId,
            param.userId,
            param.content,
            param.files,
            param.platformRole,
        )
    }
}

data class MessageSaveParams(
    val chatId: String,
    val userId: String,
    val content: String,
    val files: List<File>?,
    val platformRole: PlatformRole,
)