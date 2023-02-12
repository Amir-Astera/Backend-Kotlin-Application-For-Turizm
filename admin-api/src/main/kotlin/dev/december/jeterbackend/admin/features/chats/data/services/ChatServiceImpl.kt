package dev.december.jeterbackend.admin.features.chats.data.services

import dev.december.jeterbackend.admin.features.admin.domain.errors.AdminNotFoundFailure
import dev.december.jeterbackend.shared.features.chats.data.repositories.specifications.ChatSupportSpecification
import dev.december.jeterbackend.admin.features.chats.domain.errors.*
import dev.december.jeterbackend.admin.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.admin.data.repositories.AdminRepository
import dev.december.jeterbackend.shared.features.authorities.domain.models.AuthorityCode
import dev.december.jeterbackend.shared.features.chats.data.entities.ChatSupportEntity
import dev.december.jeterbackend.shared.features.chats.data.entities.extensions.chatSupport
import dev.december.jeterbackend.shared.features.chats.data.entities.extensions.supportMessages
import dev.december.jeterbackend.shared.features.chats.data.repositories.ChatSupportRepository
import dev.december.jeterbackend.shared.features.chats.data.repositories.SupportMessageRepository
import dev.december.jeterbackend.shared.features.chats.domain.models.MessageSortField
import dev.december.jeterbackend.shared.features.chats.domain.models.SupportChat
import dev.december.jeterbackend.shared.features.chats.domain.models.SupportChatMessage
import dev.december.jeterbackend.shared.features.chats.domain.models.SupportMessage
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
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
    private val chatSupportRepository: ChatSupportRepository,
    private val fileRepository: FileRepository,
    private val supportMessageRepository: SupportMessageRepository,
    private val adminRepository: AdminRepository
) : ChatService {
    override suspend fun getAll(
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
    ): Data<Page<SupportChatMessage>> {
        return try {
            withContext(dispatcher) {
                val pageable = PageRequest.of(page, size)
                val specification = Specification.where(ChatSupportSpecification.isGreaterThanCreatedAt(createdFrom))
                    .and(ChatSupportSpecification.isLessThanCreatedAt(createdTo))
                val chatsEntity = chatSupportRepository.findAll(specification, pageable)

                val chats = chatsEntity.map { chatEntity ->
                    chatEntity.convert<ChatSupportEntity, SupportChatMessage>(
                        mapOf(
                            "chatId" to chatEntity.id,
                            "client" to chatEntity.client?.client(),
                            "supplier" to chatEntity.supplier?.supplier(),
                            "lastMessage" to supportMessageRepository.getByChatIdAndCreatedAt(chatEntity.id, chatEntity.updatedAt)?.supportMessages()
                        )
                    ) }

                Data.Success(chats)
            }
        } catch (e: Exception) {
            Data.Error(ChatGetListFailure())
        }
    }

    override suspend fun getById(chatId: String): Data<SupportChat> {
        return try {
            withContext(dispatcher) {
                val chatEntity = chatSupportRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatCreateFailure())
                val chat = chatEntity.chatSupport()
                Data.Success(chat)
            }
        } catch (e: Exception) {
            Data.Error(ChatGetFailure())
        }
    }

    override suspend fun deleteList(ids: List<String>): Data<Unit> {
        return try {
            withContext(dispatcher){
                val chatsEntity = chatSupportRepository.findAllByIdIn(ids)

                if(chatsEntity.size != ids.size) {
                    return@withContext Data.Error(ChatNotFoundFailure())
                }
                chatSupportRepository.deleteAllByIdIn(ids)
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(ChatDeleteFailure())
        }
    }

    override suspend fun getAllMessages(chatId: String, page: Int, size: Int, searchField: String?): Data<Page<SupportMessage>> {
        return try {
            withContext(dispatcher) {
                val chatEntity = chatSupportRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatDeleteFailure())

                val sortParams = MessageSortField.CREATED_AT.getSortFields(SortDirection.DESC)
                val pageable = PageRequest.of(page, size, sortParams)
                val specification = Specification.where(ChatSupportSpecification.findAllByChatId(chatEntity)).and(
                    ChatSupportSpecification.containsMessage(searchField))
                val messageEntities = supportMessageRepository.findAll(specification, pageable)
                val messages = messageEntities.map { it.supportMessages() }
                Data.Success(messages)
            }
        } catch (e: Exception) {
            Data.Error(ChatGetAllMessagesException())
        }
    }

    override suspend fun getAllMediaFiles(userId: String, chatId: String, page: Int, size: Int): Data<Page<File>> {
        return try {
            withContext(dispatcher) {
                val chatEntity = chatSupportRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatDeleteFailure())
                val adminEntity = adminRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(AdminNotFoundFailure())
                val adminAuthority = adminEntity.adminAuthorities.map { it.authority }
                if (adminAuthority.containsAll(listOf(AuthorityCode.SUPER_ADMIN, AuthorityCode.SUPPORT))) {
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