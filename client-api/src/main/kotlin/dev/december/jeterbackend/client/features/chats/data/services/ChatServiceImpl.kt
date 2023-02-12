package dev.december.jeterbackend.client.features.chats.data.services

import dev.december.jeterbackend.client.features.appointments.domain.errors.AppointmentNotFoundFailure
import dev.december.jeterbackend.client.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.features.chats.data.repositories.ChatRepository
import dev.december.jeterbackend.shared.features.chats.data.repositories.MessageRepository
import dev.december.jeterbackend.client.features.chats.domain.errors.*
import dev.december.jeterbackend.client.features.clients.domain.errors.ClientNotFoundFailure
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.appointments.data.repositories.AppointmentRepository
import dev.december.jeterbackend.shared.features.chats.data.entities.ChatEntity
import dev.december.jeterbackend.shared.features.chats.data.entities.ChatSupportEntity
import dev.december.jeterbackend.shared.features.chats.data.entities.extensions.chat
import dev.december.jeterbackend.shared.features.chats.data.entities.extensions.messages
import dev.december.jeterbackend.shared.features.chats.data.entities.extensions.supportMessages
import dev.december.jeterbackend.shared.features.chats.data.repositories.ChatSupportRepository
import dev.december.jeterbackend.shared.features.chats.data.repositories.SupportMessageRepository
import dev.december.jeterbackend.shared.features.chats.data.repositories.specifications.ChatSpecification
import dev.december.jeterbackend.shared.features.chats.data.repositories.specifications.ChatSupportSpecification
import dev.december.jeterbackend.shared.features.chats.domain.models.*
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.tours.data.repositories.TourRepository
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.domain.errors.SupplierNotFoundFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ChatServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val supplierRepository: SupplierRepository,
    private val clientRepository: ClientRepository,
    private val chatRepository: ChatRepository,
    private val chatSupportRepository: ChatSupportRepository,
    private val fileRepository: FileRepository,
    private val messageRepository: MessageRepository,
    private val supportMessageRepository: SupportMessageRepository
) : ChatService {

    override suspend fun getAll(
        page: Int,
        size: Int,
        searchField: String?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
        status: ChatArchiveStatus?,
        userId: String
    ): Data<Page<ChatMessage>> {
        return try {
            withContext(dispatcher) {
                val client = clientRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(ClientNotFoundFailure())

                val pageable = PageRequest.of(page, size)
                val specification = Specification.where(ChatSpecification.isArchived(status))
                    .and(ChatSpecification.containsSupplierName(searchField))
                    .and(ChatSpecification.isGreaterThanCreatedAt(createdFrom))
                    .and(ChatSpecification.isLessThanCreatedAt(createdTo))
                    .and(ChatSpecification.findAllByClientId(client))
                val chatsEntity = chatRepository.findAll(specification, pageable)

                val chats = chatsEntity.map { chatEntity ->
                    chatEntity.convert<ChatEntity, ChatMessage>(mapOf(
                    "chatId" to chatEntity.id,
                    "client" to chatEntity.client.client(),
                    "supplier" to chatEntity.supplier.supplier(),
                    "lastMessage" to messageRepository.getByChatIdAndCreatedAt(chatEntity.id, chatEntity.updatedAt)?.messages()
                )) }
                Data.Success(chats)
            }
        } catch (e: Exception) {
            Data.Error(ChatGetAllException())
        }
    }

    override suspend fun getById(chatId: String): Data<Chat> {
        return try {
            withContext(dispatcher) {
                val chatEntity = chatRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatGetFailure())
                val chat = chatEntity.chat()
                Data.Success(chat)
            }
        } catch (e: Exception) {
            Data.Error(ChatGetException())
        }
    }

    override suspend fun getSupport(clientId: String, page: Int, size: Int, searchField: String?): Data<Page<SupportMessage>> {
        return try {
            withContext(dispatcher) {
                val clientEntity = clientRepository.findByIdOrNull(clientId) ?: return@withContext Data.Error(ClientNotFoundFailure())
                val chatSupportEntity = chatSupportRepository.findByClientId(clientEntity.id)

                val chatEntity = chatSupportEntity
                    ?: chatSupportRepository.save(
                        ChatSupportEntity(
                            client = clientEntity,
                            authority = PlatformRole.CLIENT
                        )
                    )
                val sortParams = MessageSortField.CREATED_AT.getSortFields(SortDirection.DESC)
                val pageable = PageRequest.of(page, size, sortParams)
                val specification = Specification.where(ChatSupportSpecification.findAllByChatId(chatEntity)).and(ChatSupportSpecification.containsMessage(searchField))
                val messageEntity = supportMessageRepository.findAll(specification, pageable)
                val message = messageEntity.map { it.supportMessages() }
                Data.Success(message)
            }
        } catch (e: Exception) {
            Data.Error(ChatGetException())
        }
    }

    override suspend fun archiveChat(chatId: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val oldEntity = chatRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatGetFailure())
                chatRepository.save(
                    oldEntity.copy(
                        archiveStatus = ChatArchiveStatus.ARCHIVED,
                        updatedAt = LocalDateTime.now()
                    )
                )
                Data.Success(Unit)
            }
        } catch(e: Exception) {
            Data.Error(ChatArchiveException())
        }
    }

    override suspend fun unarchiveChat(chatId: String): Data<Unit> {
       return try {
            withContext(dispatcher) {
                val oldEntity = chatRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatGetFailure())
                chatRepository.save(
                        oldEntity.copy(
                            archiveStatus = ChatArchiveStatus.UNARCHIVED,
                            updatedAt = LocalDateTime.now()
                        )
                    )
                Data.Success(Unit)
            }
       } catch (e: Exception) {
           Data.Error(ChatUnarchivedChatException())
       }
    }

    override suspend fun deleteChat(chatId: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val chatEntity = chatRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatDeleteFailure())
                chatRepository.deleteById(chatEntity.id)
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(ChatDeleteException())
        }
    }

    override suspend fun getAllMessages(chatId: String, page: Int, size: Int, searchField: String?): Data<Page<Message>> {
        return try {
            withContext(dispatcher) {
                val chatEntity = chatRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatDeleteFailure())
                chatRepository.save(chatEntity.copy(clientUnreadMessagesCount = 0))

                val sortParams = MessageSortField.CREATED_AT.getSortFields(SortDirection.DESC)
                val pageable = PageRequest.of(page, size, sortParams)
                val specification = Specification.where(ChatSpecification.findAllByChatId(chatEntity)).and(ChatSpecification.containsMessage(searchField))
                val messageEntities = messageRepository.findAll(specification, pageable)
                val messages = messageEntities.map { it.messages() }
                Data.Success(messages)
            }
        } catch (e: Exception) {
            Data.Error(ChatGetAllMessagesException())
        }
    }

    override suspend fun getAllMediaFiles(userId: String, chatId: String, page: Int, size: Int): Data<Page<File>> {
        return try {
            withContext(dispatcher) {
                val chatEntity = chatRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatDeleteFailure())

                if (chatEntity.client.id != userId) {
                    return@withContext Data.Error(GetChatPermissionDenied())
                }

                val sortParams = Sort.by("created_at").descending()
                val pageable = PageRequest.of(page, size, sortParams)
                val files = fileRepository.getAllByChatId(chatId, pageable).map {
                    it.convert<FileEntity, File>()
                }
                Data.Success(files)
            }
        } catch (e: Exception) {
            Data.Error(ChatGetAllMediaFilesFailure())
        }
    }
}