package dev.december.jeterbackend.shared.features.tutorials.domain.models

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.features.files.domain.models.File
import java.time.LocalDateTime

data class Tutorial(
    val id : String,
    val title : String,
    val description : String,
    val priority : Int,
    val file : File?,
    val createdAt : LocalDateTime?,
    val updatedAt : LocalDateTime,
    val authority: PlatformRole
)
