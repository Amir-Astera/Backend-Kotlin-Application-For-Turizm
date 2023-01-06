package dev.december.jeterbackend.shared.features.files.domain.models

import java.time.LocalDateTime

data class File(
    val id : String,
    val directory : FileDirectory,
    val name: String,
    val size: Long,
    val format: String,
    val url : String,
    val priority: Int,
    val createdAt: LocalDateTime,
)

