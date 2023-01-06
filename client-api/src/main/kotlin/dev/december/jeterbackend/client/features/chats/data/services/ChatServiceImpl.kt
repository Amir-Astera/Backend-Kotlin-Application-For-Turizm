package dev.december.jeterbackend.client.features.chats.data.services

import dev.december.jeterbackend.client.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.features.chats.data.repositories.ChatRepository
import dev.december.jeterbackend.shared.features.chats.data.repositories.MessageRepository
import dev.december.jeterbackend.client.features.chats.domain.errors.*
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.data.repositories.AppointmentRepository
import dev.december.jeterbackend.shared.features.tours.data.repositories.TourRepository
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatArchiveStatus
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ChatServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val supplierRepository: SupplierRepository,
    private val clientRepository: ClientRepository,
    private val chatRepository: ChatRepository,
    private val chatClientRepository: ChatRepository,
    private val fileRepository: FileRepository,
    private val appointmentRepository: AppointmentRepository,
    private val messageRepository: MessageRepository
) : ChatService {

    override suspend fun getAll(
        page: Int,
        size: Int,
        searchField: String?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
        status: ChatArchiveStatus?,
        userId: String
    ): Data<String> {//Page<ChatMessage>
        return try {
            withContext(dispatcher) {
//                val userEntity = userRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(UserNotFoundFailure())
//                val client = userEntity.client ?: return@withContext Data.Error(ClientNotFoundFailure())
//
//                val pageable = PageRequest.of(page, size)
//                val specification = Specification.where(ChatSpecification.isArchived(status))
//                    .and(ChatSpecification.containsSupplierName(searchField))
//                    .and(ChatSpecification.isGreaterThanCreatedAt(createdFrom))
//                    .and(ChatSpecification.isLessThanCreatedAt(createdTo))
//                    .and(ChatSpecification.findAllByClientId(client))
//                val chatsEntity = chatClientRepository.findAll(specification, pageable)
//
//                val chats = chatsEntity.map { chatEntity ->
//                    chatEntity.convert<ChatEntity, ChatMessage>(mapOf(
//                    "chatId" to chatEntity.id,
//                    "client" to chatEntity.client.client(),
//                    "supplier" to chatEntity.supplier.supplier(),
//                    "lastMessage" to messageRepository.getByChatIdAndCreatedAt(chatEntity.id, chatEntity.updatedAt)?.messages()
//                )) }
                Data.Success("")
            }
        } catch (e: Exception) {
            Data.Error(ChatGetAllException())
        }
    }

    override suspend fun getById(chatId: String): Data<String> {//Chat
        return try {
            withContext(dispatcher) {
//                val chatEntity = chatClientRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatGetFailure())
//                val chat = chatEntity.chat()
                Data.Success("")//chat
            }
        } catch (e: Exception) {
            Data.Error(ChatGetException())
        }
    }

    override suspend fun getSupplier(userId: String, supplierId: String): Data<Unit> {//Chat
        return try {
            withContext(dispatcher) {
//                val userEntity = userRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(UserNotFoundFailure())
//                val supplierEntity = supplierRepository.findByIdOrNull(supplierId) ?: return@withContext Data.Error(SupplierNotFoundFailure())
//                val clientEntity = userEntity.client ?: return@withContext Data.Error(ClientNotFoundFailure())
//                val appointmentsEntity = appointmentRepository.findAllByClientIdAndSupplierId(clientEntity.id, supplierEntity.id)
//                val chatEntity = chatClientRepository.findByClientIdAndSupplierId(clientEntity.id, supplierEntity.id)
//
//                val chat = if (chatEntity == null && appointmentsEntity.isNotEmpty()) {
//                    chatClientRepository.save(
//                        ChatEntity(
//                            client = clientEntity,
//                            supplier = supplierEntity,
//                            archiveStatus = ChatArchiveStatus.UNARCHIVED
//                        )
//                    ).chat()
//                } else chatEntity?.chat() ?: return@withContext Data.Error(AppointmentNotFoundFailure())
                Data.Success(Unit)//chat
            }
        } catch (e: Exception) {
            Data.Error(ChatGetException())
        }
    }

    override suspend fun archiveChat(chatId: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                val oldEntity = chatClientRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatGetFailure())
//                chatClientRepository.save(
//                    oldEntity.copy(
//                        archiveStatus = ChatArchiveStatus.ARCHIVED,
//                        updatedAt = LocalDateTime.now()
//                    )
//                )
                Data.Success(Unit)
            }
        } catch(e: Exception) {
            Data.Error(ChatArchiveException())
        }
    }

    override suspend fun unarchiveChat(chatId: String): Data<Unit> {
       return try {
            withContext(dispatcher) {
//                val oldEntity = chatClientRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatGetFailure())
//                    chatClientRepository.save(
//                        oldEntity.copy(
//                            archiveStatus = ChatArchiveStatus.UNARCHIVED,
//                            updatedAt = LocalDateTime.now()
//                        )
//                    )
                Data.Success(Unit)
            }
       } catch (e: Exception) {
           Data.Error(ChatUnarchivedChatException())
       }
    }

    override suspend fun deleteChat(chatId: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                val chatEntity = chatClientRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatDeleteFailure())
//                chatClientRepository.deleteById(chatEntity.id)
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(ChatDeleteException())
        }
    }

    override suspend fun getAllMessages(chatId: String, page: Int, size: Int, searchField: String?): Data<Unit> {//Page<Message>
        return try {
            withContext(dispatcher) {
//                val chatEntity = chatRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatDeleteFailure())
//                chatRepository.save(chatEntity.copy(clientUnreadMessagesCount = 0))
//
//                val sortParams = MessageSortField.CREATED_AT.getSortFields(SortDirection.DESC)
//                val pageable = PageRequest.of(page, size, sortParams)
//                val specification = Specification.where(ChatSpecification.findAllByChatId(chatEntity)).and(ChatSpecification.containsMessage(searchField))
//                val messageEntities = messageRepository.findAll(specification, pageable)
//                val messages = messageEntities.map { it.messages() }
                Data.Success(Unit)//messages
            }
        } catch (e: Exception) {
            Data.Error(ChatGetAllMessagesException())
        }
    }

    override suspend fun getAllMediaFiles(userId: String, chatId: String, page: Int, size: Int): Data<Unit> {//Page<File>
        return try {
            withContext(dispatcher) {
//                val chatEntity = chatClientRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatDeleteFailure())
//
//                if (chatEntity.client.id != userId) {
//                    return@withContext Data.Error(GetChatPermissionDenied())
//                }
//
//                val sortParams = Sort.by("created_at").descending()
//                val pageable = PageRequest.of(page, size, sortParams)
//                val files = fileRepository.getAllByChatId(chatId, pageable).map {
//                    it.convert<FileEntity, File>()
//                }
                Data.Success(Unit)//files
            }
        } catch (e: Exception) {
            Data.Error(ChatGetAllMediaFilesFailure())
        }
    }
}