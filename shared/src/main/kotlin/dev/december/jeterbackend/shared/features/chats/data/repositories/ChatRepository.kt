package dev.december.jeterbackend.shared.features.chats.data.repositories

import dev.december.jeterbackend.shared.features.chats.data.entities.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ChatRepository: JpaRepository<ChatEntity, String>,
    JpaSpecificationExecutor<ChatEntity> {
        fun findByClientIdAndSupplierId(client_id: String, supplier_id: String): ChatEntity?
}