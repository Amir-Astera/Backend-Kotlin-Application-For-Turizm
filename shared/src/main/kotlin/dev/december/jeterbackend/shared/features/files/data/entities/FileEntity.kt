package dev.december.jeterbackend.shared.features.files.data.entities

import dev.december.jeterbackend.shared.features.files.domain.models.FileDirectory
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "file")
data class FileEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id : String = UUID.randomUUID().toString(),
    @Enumerated(EnumType.STRING)
    @Column(name = "directory", nullable = false)
    val directory : FileDirectory,
    @Column(name = "format", nullable = false)
    val format: String,
    @Column(name = "url", nullable = false)
    val url : String,
    @Column(name = "priority", nullable = false)
    val priority: Int,
    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
