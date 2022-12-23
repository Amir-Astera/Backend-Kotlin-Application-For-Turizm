package dev.december.jeterbackend.shared.features.faq.domain.models

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import java.time.LocalDateTime

data class FAQ(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime,
    val authority: PlatformRole
)
