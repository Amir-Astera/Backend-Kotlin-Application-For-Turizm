package dev.december.jeterbackend.stream.signaler.data.services

import dev.december.jeterbackend.stream.signaler.errors.ChatNotFoundFailure
import dev.december.jeterbackend.stream.signaler.data.model.ResponseMessageModel
import dev.december.jeterbackend.stream.signaler.domain.services.MessageService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.data.entities.MessageEntity
import dev.december.jeterbackend.shared.features.chats.data.entities.extensions.messages
import dev.december.jeterbackend.shared.features.chats.data.repositories.ChatRepository
import dev.december.jeterbackend.shared.features.chats.data.repositories.MessageRepository
import dev.december.jeterbackend.shared.features.chats.domain.models.MessageStatus
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val supplierRepository: SupplierRepository,
    private val clientRepository: ClientRepository,
    private val chatRepository: ChatRepository
): MessageService {
    override fun save(chatId: String, userId: String, message: String, platformRole: PlatformRole): Data<ResponseMessageModel> {
        return try {
            val supplierEntity = supplierRepository.findByIdOrNull(userId)
            val clientEntity = clientRepository.findByIdOrNull(userId)
            val chatEntity = chatRepository.findByIdOrNull(chatId)
            val messages = messageRepository.save(
                MessageEntity(
                    supplier = supplierEntity,
                    client = clientEntity,
                    chat = chatEntity,
                    content = message,
                    status = MessageStatus.DELIVERED
                )
            )



            chatRepository.save(
                chatEntity!!.copy(messages = chatEntity.messages?.plus(messages), unreadMessagesCount = chatEntity.unreadMessagesCount.inc())
            )


            val response = if(platformRole == PlatformRole.CLIENT) {
                ResponseMessageModel(
                    client = messages.messages().client,
                    supplier = null,
                    message = message
                )
            } else {
                ResponseMessageModel(
                    client = null,
                    supplier = messages.messages().supplier,
                    message = message
                )
            }


            Data.Success(response)
        } catch (e: Exception) {
            return Data.Error(ChatNotFoundFailure())
        }
    }


}