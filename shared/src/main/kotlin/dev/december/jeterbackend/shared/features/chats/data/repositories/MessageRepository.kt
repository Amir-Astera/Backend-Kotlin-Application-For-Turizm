package dev.december.jeterbackend.shared.features.chats.data.repositories

import dev.december.jeterbackend.shared.features.chats.data.entities.MessageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository: JpaRepository<MessageEntity, String>,
    JpaSpecificationExecutor<MessageEntity> {
}