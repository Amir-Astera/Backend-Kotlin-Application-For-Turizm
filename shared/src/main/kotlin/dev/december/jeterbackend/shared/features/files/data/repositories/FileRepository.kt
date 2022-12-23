package dev.december.jeterbackend.shared.features.files.data.repositories

import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FileRepository : CrudRepository<FileEntity, String> {
}