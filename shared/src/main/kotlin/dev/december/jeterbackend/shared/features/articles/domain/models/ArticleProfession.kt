package dev.december.jeterbackend.shared.features.articles.domain.models

import dev.december.jeterbackend.shared.features.files.domain.models.File
import java.time.LocalDateTime

data class ArticleProfession(
    val id: String,
    val title: String,
    val description: String,
    val files: List<File> = emptyList(),
    val priority: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime,
)