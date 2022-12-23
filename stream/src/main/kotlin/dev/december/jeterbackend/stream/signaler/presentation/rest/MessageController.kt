package dev.december.jeterbackend.stream.signaler.presentation.rest

import dev.december.jeterbackend.stream.signaler.data.model.MessageModel
import dev.december.jeterbackend.stream.signaler.domain.usecases.MessageSaveParams
import dev.december.jeterbackend.stream.signaler.domain.usecases.MessageSaveUseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.security.Principal


@Controller
class MessageController(
    private val messagingTemplate: SimpMessagingTemplate,
    private val messageSaveUseCase: MessageSaveUseCase
) {
    @MessageMapping("/room")
    fun sendToSpecificUser(@Payload message: MessageModel, principal: Principal) {
        val responseMessage = messageSaveUseCase(MessageSaveParams(message.chatId, principal.name, message.message, message.platformRole))
        val response = responseMessage.let {
            when (it) {
                is Data.Success -> {
                    it.data
                }
                is Data.Error -> {
                    it.failure
                }
            }
        }
        messagingTemplate.convertAndSendToUser(message.userId,"/topic/message", response)
    }
}