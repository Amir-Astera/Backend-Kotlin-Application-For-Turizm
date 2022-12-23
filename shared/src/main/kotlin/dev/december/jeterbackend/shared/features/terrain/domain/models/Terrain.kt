package dev.december.jeterbackend.shared.features.terrain.domain.models

import dev.december.jeterbackend.shared.features.files.domain.models.File
import java.time.LocalDateTime

data class Terrain(
    val id: String,
    val title: String,
    val priority: Int,
    val file: File?,
    val description: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime
)
