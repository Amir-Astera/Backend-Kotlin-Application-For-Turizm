package dev.december.jeterbackend.stream.signaler.domain.usecases

import dev.december.jeterbackend.stream.core.usecases.UseCase
import dev.december.jeterbackend.stream.signaler.data.model.ResponseMessageModel
import dev.december.jeterbackend.stream.signaler.domain.services.MessageService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class MessageSaveUseCase(
    private val messageService: MessageService
): UseCase<ResponseMessageModel, MessageSaveParams> {
    override fun invoke(param: MessageSaveParams): Data<ResponseMessageModel> {
        return messageService.save(param.chatId, param.userId, param.message, param.platformRole)
    }
}

data class MessageSaveParams(
    val chatId: String,
    val userId: String,
    val message: String,
    val platformRole: PlatformRole
)