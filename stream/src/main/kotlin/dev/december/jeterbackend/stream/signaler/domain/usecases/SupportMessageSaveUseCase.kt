package dev.december.jeterbackend.stream.signaler.domain.usecases

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.SupportMessage
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.stream.core.domain.model.PlatformRole
import dev.december.jeterbackend.stream.core.usecases.UseCase
import dev.december.jeterbackend.stream.signaler.domain.services.MessageService
import org.springframework.stereotype.Component

@Component
class SupportMessageSaveUseCase(
    private val messageService: MessageService
): UseCase<SupportMessage, SupportMessageSaveParams> {
    override fun invoke(param: SupportMessageSaveParams): Data<SupportMessage> {
        return messageService.saveSupportMessage(
            param.chatId,
            param.userId,
            param.content,
            param.files,
            param.platformRole
        )
    }

}

data class SupportMessageSaveParams(
    val chatId: String,
    val userId: String,
    val content: String,
    val files: List<File>?,
    val platformRole: PlatformRole,
)