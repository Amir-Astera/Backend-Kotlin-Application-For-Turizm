package dev.december.jeterbackend.shared.features.files.data.repositories

import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FileRepository : JpaRepository<FileEntity, String>,
    JpaSpecificationExecutor<FileEntity> {

    // too weak at jpql
    @Query(
        "select * from file where file.id in (select file_id from message_files where message_files.message_id in (select id from messages where messages.chat_id=:chatId))",
        nativeQuery=true,
    )
    fun getAllByChatId(chatId: String, pageable: Pageable): Page<FileEntity>
}