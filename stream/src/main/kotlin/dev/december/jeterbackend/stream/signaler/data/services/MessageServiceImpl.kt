package dev.december.jeterbackend.stream.signaler.data.services

import dev.december.jeterbackend.stream.signaler.errors.ChatNotFoundFailure
import dev.december.jeterbackend.stream.signaler.data.model.ResponseMessageModel
import dev.december.jeterbackend.stream.signaler.domain.services.MessageService
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.admin.data.entities.AdminEntity
import dev.december.jeterbackend.shared.features.admin.data.repositories.AdminRepository
import dev.december.jeterbackend.shared.features.chats.data.entities.ChatEntity
import dev.december.jeterbackend.shared.features.chats.data.entities.ChatSupportEntity
import dev.december.jeterbackend.shared.features.chats.data.entities.MessageEntity
import dev.december.jeterbackend.shared.features.chats.data.entities.SupportMessageEntity
import dev.december.jeterbackend.shared.features.chats.data.entities.extensions.messages
import dev.december.jeterbackend.shared.features.chats.data.entities.extensions.supportMessages
import dev.december.jeterbackend.shared.features.chats.data.repositories.ChatRepository
import dev.december.jeterbackend.shared.features.chats.data.repositories.ChatSupportRepository
import dev.december.jeterbackend.shared.features.chats.data.repositories.MessageRepository
import dev.december.jeterbackend.shared.features.chats.data.repositories.SupportMessageRepository
import dev.december.jeterbackend.shared.features.chats.domain.models.*
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.stream.core.domain.model.PlatformRole
import dev.december.jeterbackend.stream.signaler.errors.MessageSendFailure
import dev.december.jeterbackend.stream.signaler.errors.UserNotFoundFailure
import org.springframework.data.repository.findByIdOrNull
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val supplierRepository: SupplierRepository,
    private val clientRepository: ClientRepository,
    private val chatRepository: ChatRepository,
    private val chatSupportRepository: ChatSupportRepository,
    private val supportMessageRepository: SupportMessageRepository,
    private val fileRepository: FileRepository,
    private val adminRepository: AdminRepository,
    private val messagingTemplate: SimpMessagingTemplate,
) : MessageService {
    override fun save(
        chatId: String,
        userId: String,
        content: String,
        files: List<File>?,
        platformRole: PlatformRole,
    ): Data<Message> {
        return try {
            val entityPair: Pair<ClientEntity?, SupplierEntity?> = when (platformRole) {
                PlatformRole.CLIENT -> {
                    val entity = clientRepository.findByIdOrNull(userId) ?: return Data.Error(UserNotFoundFailure())
                    Pair(entity, null)
                }
                PlatformRole.SUPPLIER -> {
                    val entity = supplierRepository.findByIdOrNull(userId) ?: return Data.Error(UserNotFoundFailure())
                    Pair(null, entity)
                }
                else -> {
                    return Data.Error(UserNotFoundFailure())
                }
            }
            val chatEntity = chatRepository.findByIdOrNull(chatId)
                ?: return Data.Error(ChatNotFoundFailure())
            val fileEntities = files?.let { fileRepository.findAllById(it.map { file -> file.id }) }
            val messageEntity = messageRepository.save(
                MessageEntity(
                    client = entityPair.first,
                    supplier = entityPair.second,
                    chat = chatEntity,
                    content = content,
                    files = fileEntities?.toSet() ?: emptySet(),
                    status = MessageStatus.DELIVERED,
                )
            )
            val chat = chatRepository.save(
                chatEntity.copy(
                    clientUnreadMessagesCount = if (platformRole == PlatformRole.SUPPLIER) chatEntity.clientUnreadMessagesCount.inc() else chatEntity.clientUnreadMessagesCount,
                    supplierUnreadMessagesCount = if (platformRole == PlatformRole.CLIENT) chatEntity.supplierUnreadMessagesCount.inc() else chatEntity.supplierUnreadMessagesCount,
                    updatedAt = messageEntity.createdAt,
                )
            ).convert<ChatEntity, ChatMessage>(
                mapOf(
                    "chatId" to chatEntity.id,
                    "client" to chatEntity.client.client(),
                    "supplier" to chatEntity.supplier.supplier()
                )
            )

            val message = messageEntity.messages()


            messagingTemplate.convertAndSendToUser(chat.supplier.id, "/topic/message", message)
            messagingTemplate.convertAndSendToUser(chat.client.id, "/topic/message", message)
            Data.Success(message)
        } catch (e: Exception) {
            messagingTemplate.convertAndSendToUser(userId, "/topic/message", MessageSendFailure())
            return Data.Error(MessageSendFailure())
        }
    }

    override fun saveSupportMessage(chatId: String, userId: String, content: String, files: List<File>?, platformRole: PlatformRole): Data<SupportMessage> {
        return try {
            val chatEntity = chatSupportRepository.findByIdOrNull(chatId) ?: return Data.Error(ChatNotFoundFailure())
            val entityPair: Pair<AdminEntity?, Pair<ClientEntity?, SupplierEntity?>?> = when (platformRole) {
                PlatformRole.ADMIN -> {
                    val entity = adminRepository.findByIdOrNull(userId) ?: return Data.Error(UserNotFoundFailure())
                    Pair(entity, null)
                }
                PlatformRole.CLIENT -> {
                    val entity = clientRepository.findByIdOrNull(userId) ?: return Data.Error(UserNotFoundFailure())
                    Pair(null, Pair(entity, null))
                }
                PlatformRole.SUPPLIER -> {
                    val entity = supplierRepository.findByIdOrNull(userId) ?: return Data.Error(UserNotFoundFailure())
                    Pair(null, Pair(null, entity))
                }
            }
            val fileEntities = files?.let { fileRepository.findAllById(it.map { file -> file.id }) }
            val messageEntity = supportMessageRepository.save(
                SupportMessageEntity(
                    client = entityPair.second?.first,
                    supplier = entityPair.second?.second,
                    admin = entityPair.first,
                    chat = chatEntity,
                    content = content,
                    files = fileEntities?.toSet() ?: emptySet(),
                    status = MessageStatus.DELIVERED,
                )
            )
            val chat = chatSupportRepository.save(
                chatEntity.copy(
                    updatedAt = messageEntity.createdAt
                )
            ).convert<ChatSupportEntity, SupportChatMessage>(
                mapOf(
                    "chatId" to chatEntity.id,
                    "client" to chatEntity.client?.client(),
                    "supplier" to chatEntity.supplier?.supplier()
                )
            )

            val message = messageEntity.supportMessages()


            messagingTemplate.convertAndSendToUser(chat.supplier?.id ?: chat.client!!.id , "/topic/message", message)
            messagingTemplate.convertAndSend("/topic/all", message)
            Data.Success(message)
        } catch(e: Exception) {
            if(platformRole == PlatformRole.ADMIN) messagingTemplate.convertAndSend("/topic/all", MessageSendFailure())
            else messagingTemplate.convertAndSendToUser(userId , "/topic/message", MessageSendFailure())
            Data.Error(MessageSendFailure())
        }
    }
}