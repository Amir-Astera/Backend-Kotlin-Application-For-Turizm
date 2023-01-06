package dev.december.jeterbackend.supplier.features.chats.presentation.rest

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.core.config.security.SessionUser
import dev.december.jeterbackend.supplier.features.chats.domain.usecases.*
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.time.LocalDateTime


@RestController
@RequestMapping("/api/chat/supplierApi")
@Tag(name = "chat", description = "The Chats API")
class ChatController(
    private val getAllChatUseCase: GetAllChatUseCase,
//    private val getChatUseCase: GetChatUseCase,
    private val getClientChatUseCase: GetClientChatUseCase,
    private val archivedChatsUseCase: GetArchivedChatsUseCase,
    private val archivedChatUseCase: GetArchivedChatUseCase,
    private val archiveChatUseCase: ArchiveChatUseCase,
    private val unArchiveChatUseCase: UnArchiveChatUseCase,
    private val deleteChatUseCase: DeleteChatUseCase,
    private val getAllMessagesUseCase: GetAllMessagesUseCase,
    private val getChatMediaFilesUseCase: GetChatMediaFilesUseCase,
) {

    @SecurityRequirement(name = "security_auth")
    @GetMapping("/getAll")
    fun getAll(
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        @RequestParam(required = false)
        searchField: String?,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdFrom: LocalDateTime?,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdTo: LocalDateTime?,
        @Parameter authentication: Authentication
        ): Mono<ResponseEntity<Any>> {
        val supplier = authentication.principal as SessionUser
        val supplierId = supplier.id
        return mono { getAllChatUseCase(
            GetAllChatParams(
                page,
                size,
                searchField,
                createdFrom,
                createdTo,
                supplierId
            )
         )
        }.map {
            when (it) {
                is Data.Success ->{
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

//    @SecurityRequirement(name = "security_auth")
//    @GetMapping("/get/{chatId}")
//    fun get(
//        @PathVariable chatId: String
//    ): Mono<ResponseEntity<Any>> {
//        return mono { getChatUseCase(GetChatParams(chatId)) }.map {
//            when (it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().body(it.data)
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure)
//                }
//            }
//        }
//    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping("/supplier/{supplierId}/client")
    fun getClient(
        @PathVariable supplierId: String,
        @Parameter(hidden = true) authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val client = authentication.principal as SessionUser
        val clientId = client.id
        return mono { getClientChatUseCase(GetClientChatParams(supplierId, clientId)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping("/archived_chats")
    fun getArchivedChats(
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        @Parameter(hidden = true) authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val supplier = authentication.principal as SessionUser
        val supplierId = supplier.id
        return mono { archivedChatsUseCase(GetArchivedChatsParams(page, size, supplierId)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping("/archived_chat/{chatId}")
    fun getArchivedChat(
        @PathVariable chatId: String
    ): Mono<ResponseEntity<Any>> {
        return mono { archivedChatUseCase(GetArchivedChatParams(chatId)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/archiveChat/{chatId}")
    fun archiveChat(
        @PathVariable chatId: String
    ): Mono<ResponseEntity<Any>> {
        return mono { archiveChatUseCase(ArchiveChatParams(chatId)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/unArchiveChat/{chatId}")
    fun unArchiveChat(
        @PathVariable chatId: String
    ): Mono<ResponseEntity<Any>> {
        return mono { unArchiveChatUseCase(UnArchiveChatParams(chatId)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @DeleteMapping("delete/{chatId}")
    fun deleteChat(
        @PathVariable chatId: String
    ): Mono<ResponseEntity<Any>> {
        return mono { deleteChatUseCase(DeleteChatParams(chatId)) }.map{
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping("/{chatId}/messages")
    fun getAllMessages(
        @PathVariable chatId: String,
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        @RequestParam(required = false)
        searchField: String?
    ): Mono<ResponseEntity<Any>> {
        return mono { getAllMessagesUseCase(
            GetAllMessagesChatParams(chatId, page, size, searchField)
        ) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping("/{chatId}/media")
    fun getAllMediaFiles(
        @PathVariable chatId: String,
        @Parameter(hidden = true) authentication: Authentication,
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id
        return mono { getChatMediaFilesUseCase(
            GetChatMediaFilesParams(userId, chatId, page, size)
        ) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

}