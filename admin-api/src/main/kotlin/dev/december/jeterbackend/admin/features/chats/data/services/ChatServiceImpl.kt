package dev.december.jeterbackend.admin.features.chats.data.services

import dev.december.jeterbackend.shared.features.chats.data.repositories.specifications.ChatSupportSpecification
import dev.december.jeterbackend.admin.features.chats.domain.errors.*
import dev.december.jeterbackend.admin.features.chats.domain.services.ChatService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.chats.data.entities.ChatSupportEntity
import dev.december.jeterbackend.shared.features.chats.data.repositories.ChatSupportRepository
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatSupport
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ChatServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val chatRepository: ChatSupportRepository,
    private val supplierRepository: SupplierRepository,
    private val clientRepository: ClientRepository,
) : ChatService {
    override suspend fun getAll(
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
    ): Data<Page<ChatSupport>> {
        return try {
            withContext(dispatcher) {

                val pageable = PageRequest.of(page, size)
                val specification = Specification.where(ChatSupportSpecification.isGreaterThanCreatedAt(createdFrom)?.and(
                    ChatSupportSpecification.isLessThanCreatedAt(createdTo)))

            val chatsEntity = chatRepository.findAll(specification, pageable)

            val chats = chatsEntity.map { it.convert<ChatSupportEntity, ChatSupport>(
                mapOf(
                    "chatId" to it.id,
                    "supplierId" to it.supplier?.supplier(),
                    "clientId" to it.client?.client()
                )
            ) }

            Data.Success(chats)
            }
        } catch (e: Exception) {
            Data.Error(ChatGetListFailure())
        }
    }

    override suspend fun getById(chatId: String): Data<ChatSupport> {
        return try {
            withContext(dispatcher) {
                val chatEntity = chatRepository.findByIdOrNull(chatId) ?: return@withContext Data.Error(ChatCreateFailure())

                val chat = chatEntity.convert<ChatSupportEntity, ChatSupport>(
                    mapOf(
                        "chatId" to chatEntity.id,
                        "supplierId" to chatEntity.supplier?.supplier(),
                        "clientId" to chatEntity.client?.client()
                    )
                )
                Data.Success(chat)
            }
        } catch (e: Exception) {
            Data.Error(ChatGetFailure())
        }
    }

    override suspend fun getClient(clientId: String): Data<ChatSupport> {
        return try {
            withContext(dispatcher){
                val clientEntity = clientRepository.findByIdOrNull(clientId)?: return@withContext Data.Error(ChatNotFoundFailure())
                val chatEntity = chatRepository.findByClientId(clientEntity.id)
                    ?: chatRepository.save(
                            ChatSupportEntity(
                                client = clientEntity,
                                authority = PlatformRole.CLIENT
                            )
                        )
//                        .apply {
//                        config.pn.publish(
//                            message = "Пожалуйста напишите нам свой вопрос, мы ответим вам в течение нескольких секунд",
//                            channel = this.id,
//                        ).async { result, status ->
//                            if (!status.error) {
//                                println("Publish timetoken ${result!!.timetoken}")
//                            }
//                            println("Status code ${status.statusCode}")
//                        }
//                    }

                val chat = chatEntity.convert<ChatSupportEntity, ChatSupport>(
                    mapOf(
                        "chatId" to chatEntity.id,
                        "supplierId" to chatEntity.supplier?.supplier(),
                        "clientId" to chatEntity.client?.client()
                    )
                )
                Data.Success(chat)
            }
        } catch (e: Exception) {
            Data.Error(ChatGetClientFailure())
        }
    }

    override suspend fun getSupplier(supplierId: String): Data<ChatSupport> {
        return try {
            withContext(dispatcher){
                val supplierEntity = supplierRepository.findByIdOrNull(supplierId) ?: return@withContext Data.Error(ChatGetFailure())
                val chatEntity = chatRepository.findBySupplierId(supplierEntity.id)
                    ?: chatRepository.save(
                        ChatSupportEntity(
                            supplier = supplierEntity,
                            authority = PlatformRole.SUPPLIER
                        )
                    )
//                        .apply {
//                        config.pn.publish(
//                            message = "Пожалуйста напишите нам свой вопрос, мы ответим вам в течение нескольких секунд",
//                            channel = this.id,
//                        ).async { result, status ->
//                            if (!status.error) {
//                                println("Publish timetoken ${result!!.timetoken}")
//                            }
//                            println("Status code ${status.statusCode}")
//                        }
//                    }

                val chat = chatEntity.convert<ChatSupportEntity, ChatSupport>(
                    mapOf(
                        "chatId" to chatEntity.id,
                        "supplierId" to chatEntity.supplier?.supplier(),
                        "clientId" to chatEntity.client?.client()
                    )
                )
                Data.Success(chat)
            }
        }
        catch (e: Exception){
            Data.Error(ChatGetSupplierFailure())
        }
    }

    override suspend fun deleteList(ids: List<String>): Data<Unit> {
        return try {
            withContext(dispatcher){
                val chatsEntity = chatRepository.findAllByIdIn(ids)

                if(chatsEntity.size != ids.size) {
                    return@withContext Data.Error(ChatNotFoundFailure())
                }
                chatRepository.deleteAllByIdIn(ids)
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(ChatDeleteFailure())
        }
    }
}