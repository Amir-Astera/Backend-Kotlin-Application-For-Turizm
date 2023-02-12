package dev.december.jeterbackend.stream.signaler.presentation.rest

import dev.december.jeterbackend.stream.signaler.data.model.MessageModel
import dev.december.jeterbackend.stream.signaler.domain.usecases.MessageSaveParams
import dev.december.jeterbackend.stream.signaler.domain.usecases.MessageSaveUseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.stream.signaler.config.PrincipalUser
import dev.december.jeterbackend.stream.signaler.domain.usecases.SupportMessageSaveParams
import dev.december.jeterbackend.stream.signaler.domain.usecases.SupportMessageSaveUseCase
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.security.Principal


@Controller
class MessageController(
    private val messageSaveUseCase: MessageSaveUseCase,
    private val supportMessageSaveUseCase: SupportMessageSaveUseCase
) {
    @MessageMapping("/room")
    fun sendToSpecificUser(@Payload content: MessageModel, principal: Principal) {
        val principalUser = principal as PrincipalUser
        messageSaveUseCase(
            MessageSaveParams(
                content.chatId,
                principalUser.id,
                content.content,
                content.files,
                principalUser.platformRole,
            )
        )
    }

    @MessageMapping("/group")
    fun sendToAdmin(@Payload content: MessageModel, principal: Principal) {
        val principalUser = principal as PrincipalUser
        supportMessageSaveUseCase(
            SupportMessageSaveParams(
                content.chatId,
                principalUser.id,
                content.content,
                content.files,
                principalUser.platformRole,
            )
        )
    }
}