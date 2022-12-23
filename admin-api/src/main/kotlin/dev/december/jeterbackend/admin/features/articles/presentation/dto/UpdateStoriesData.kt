package dev.december.jeterbackend.admin.features.articles.presentation.dto

import dev.december.jeterbackend.shared.features.files.domain.models.File

data class UpdateStoriesData(
    val title: String?,
    val priority: Int?,
    val html_content: String?,
    val files: List<File>?,
)