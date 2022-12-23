package dev.december.jeterbackend.admin.features.articles.presentation.dto

import dev.december.jeterbackend.shared.features.files.domain.models.File

data class UpdateArticleData(
    val title: String?,
    val priority: Int?,
    val files: List<File>?,
)