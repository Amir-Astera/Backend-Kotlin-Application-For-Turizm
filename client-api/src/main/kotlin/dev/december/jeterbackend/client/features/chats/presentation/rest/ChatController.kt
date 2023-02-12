package dev.december.jeterbackend.client.features.chats.presentation.rest

import dev.december.jeterbackend.client.features.chats.domain.usecases.GetAllChatUseCase
import dev.december.jeterbackend.client.core.config.security.SessionUser
import dev.december.jeterbackend.client.features.chats.domain.usecases.*
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatArchiveStatus
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
@RequestMapping("/api/chat")
@Tag(name = "chats", description = "The Chats API")
class ChatController(
    private val getAllChatUseCase: GetAllChatUseCase,
    private val getChatUseCase: GetChatUseCase,
    private val getSupportUseCase: GetSupportChatUseCase,
    private val archiveChatUseCase: ArchiveChatUseCase,
    private val unArchiveChatUseCase: UnArchiveChatUseCase,
    private val deleteChatUseCase: DeleteChatUseCase,
    private val getAllMessagesUseCase: GetAllMessagesUseCase,
    private val getChatMediaFilesUseCase: GetChatMediaFilesUseCase
    ) {
    @SecurityRequirement(name = "security_auth")
    @GetMapping
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
        @RequestParam(required = false)
        status: ChatArchiveStatus?,
        @Parameter authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id
        return mono { getAllChatUseCase(
            GetAllChatParams(
                page,
                size,
                searchField,
                createdFrom,
                createdTo,
                status,
                userId
            )
        ) }.map {
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

    @SecurityRequirement(name = "security_auth")
    @GetMapping("/{chatId}")
    fun get(
        @PathVariable chatId: String,
    ): Mono<ResponseEntity<Any>> {
        return mono { getChatUseCase(
            GetChatParams(chatId)
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
    @GetMapping("/support")
    fun getSupport(
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        @RequestParam(required = false)
        searchField: String?,
        @Parameter(hidden = true) authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val clientId = user.id
        return mono { getSupportUseCase(
            GetSupportChatParams(
                clientId,
                page,
                size,
                searchField
            )
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
    @PutMapping("/archiveChat/{chatId}")
    fun archiveChat(
        @PathVariable chatId: String
    ): Mono<ResponseEntity<Any>> {
        return mono { archiveChatUseCase(
            ArchiveChatParams(chatId)
        ) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
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
        return mono { unArchiveChatUseCase(
            UnArchiveChatParams(chatId)
        ) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @DeleteMapping("/{chatId}")
    fun deleteChat(
        @PathVariable chatId: String
    ): Mono<ResponseEntity<Any>> {
        return mono { deleteChatUseCase(
            DeleteChatParams(chatId)
        ) }.map{
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
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
        searchField: String?,
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