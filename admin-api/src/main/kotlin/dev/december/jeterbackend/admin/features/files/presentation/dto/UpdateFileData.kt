package dev.december.jeterbackend.admin.features.files.presentation.dto

import dev.december.jeterbackend.shared.features.files.domain.models.FileDirectory
import org.springframework.http.codec.multipart.FilePart

data class UpdateFileData(
    val directory: FileDirectory?,
    val priority: Int?,
    val part: FilePart?
)