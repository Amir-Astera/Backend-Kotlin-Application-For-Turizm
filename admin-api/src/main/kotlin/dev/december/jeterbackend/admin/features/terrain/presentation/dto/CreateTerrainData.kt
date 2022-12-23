package dev.december.jeterbackend.admin.features.terrain.presentation.dto

import dev.december.jeterbackend.shared.features.files.domain.models.File

data class CreateTerrainData(
    val title: String,
    val specialistIds: List<String>?,
    val priority: Int,
    val description: String,
    val file: File?,
)
