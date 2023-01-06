package dev.december.jeterbackend.shared.features.chats.data.repositories

import dev.december.jeterbackend.shared.features.chats.data.entities.MessageEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface MessageRepository: JpaRepository<MessageEntity, String>,
    JpaSpecificationExecutor<MessageEntity> {

    fun getByChatIdAndCreatedAt(chatId: String, createdAt: LocalDateTime): MessageEntity?

    fun findAllByChatIdAndFilesNotNull(chatId: String, pageable: Pageable): Page<MessageEntity>
}