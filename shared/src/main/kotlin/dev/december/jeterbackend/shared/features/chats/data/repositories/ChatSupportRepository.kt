package dev.december.jeterbackend.shared.features.chats.data.repositories

import dev.december.jeterbackend.shared.features.chats.data.entities.ChatSupportEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface ChatSupportRepository: JpaRepository<ChatSupportEntity, String>,
    JpaSpecificationExecutor<ChatSupportEntity> {
    fun findBySupplierId(supplier_id: String): ChatSupportEntity?
    fun findByClientId(client_id: String): ChatSupportEntity?
    fun findAllByIdIn(ids: List<String>): List<ChatSupportEntity>
    @Transactional
    fun deleteAllByIdIn(ids: List<String>)
}