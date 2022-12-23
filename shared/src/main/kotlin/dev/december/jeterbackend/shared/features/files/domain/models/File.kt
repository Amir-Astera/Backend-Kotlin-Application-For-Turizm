package dev.december.jeterbackend.shared.features.files.domain.models

data class File(
    val id : String,
    val directory : FileDirectory,
    val format: String,
    val url : String,
    val priority: Int,
)

