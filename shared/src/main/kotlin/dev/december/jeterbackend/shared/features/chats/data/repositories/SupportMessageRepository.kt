package dev.december.jeterbackend.shared.features.chats.data.repositories

import dev.december.jeterbackend.shared.features.chats.data.entities.SupportMessageEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface SupportMessageRepository: JpaRepository<SupportMessageEntity, String>,
    JpaSpecificationExecutor<SupportMessageEntity> {

    fun getByChatIdAndCreatedAt(chatId: String, createdAt: LocalDateTime): SupportMessageEntity?

    fun findAllByChatIdAndFilesNotNull(chatId: String, pageable: Pageable): Page<SupportMessageEntity>
}