package dev.december.jeterbackend.shared.features.stories.domain.models

import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import java.time.LocalDateTime

data class Stories(
    val id: String,
    val title: String,
    val priority: Int,
    val html_content: String,
    val files: List<File> = emptyList(),
    val suppliers: Set<Supplier>? = emptySet(),//Admin
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)