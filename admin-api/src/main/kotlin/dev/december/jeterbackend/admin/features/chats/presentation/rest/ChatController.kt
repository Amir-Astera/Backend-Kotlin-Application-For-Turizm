package dev.december.jeterbackend.admin.features.chats.presentation.rest

import dev.december.jeterbackend.admin.features.chats.domain.usecases.GetAllMessagesChatParams
import dev.december.jeterbackend.admin.features.chats.domain.usecases.GetAllMessagesUseCase
import dev.december.jeterbackend.admin.features.chats.domain.usecases.GetChatMediaFilesParams
import dev.december.jeterbackend.admin.features.chats.domain.usecases.GetChatMediaFilesUseCase
import dev.december.jeterbackend.admin.core.config.security.SessionUser
import dev.december.jeterbackend.admin.features.chats.domain.usecases.*
import dev.december.jeterbackend.admin.features.chats.presentation.dto.DeleteChatListData
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
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
    private val getByIdChatUseCase: GetByIdChatUseCase,
    private val deleteChatListUseCase: DeleteChatListUseCase,
    private val getAllMessagesUseCase: GetAllMessagesUseCase,
    private val getChatMediaFilesUseCase: GetChatMediaFilesUseCase
) {
    @SecurityRequirement(name = "security_auth")
    @GetMapping("/chats")
    fun getAll(
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdFrom: LocalDateTime?,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdTo: LocalDateTime?
    ): Mono<ResponseEntity<Any>> {
        return mono { getAllChatUseCase(
            GetAllChatParams(
                page,
                size,
                createdFrom,
                createdTo
            )
        )
        }.map {
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
    @GetMapping("/{chatId}")
    fun get(
        @PathVariable chatId: String,
    ): Mono<ResponseEntity<Any>> {
        return mono { getByIdChatUseCase(GetByIdChatParams(chatId)) }.map {
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
    @DeleteMapping
    fun deleteList(
        @RequestBody data: DeleteChatListData,
    ): Mono<ResponseEntity<Any>> {
        return mono { deleteChatListUseCase(data.convert()) }.map {
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
        val userId = user.adminId
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