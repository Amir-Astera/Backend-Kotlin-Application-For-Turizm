package dev.december.jeterbackend.shared.features.articles.domain.models

import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.stories.domain.models.Stories
import java.time.LocalDateTime

data class Article(
    val id: String,
    val title: String,
    val priority: Int,
    val files: List<File> = emptyList(),
    val stories: List<Stories> = emptyList(),
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)