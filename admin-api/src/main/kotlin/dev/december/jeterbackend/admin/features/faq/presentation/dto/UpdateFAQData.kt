package dev.bytepride.truprobackend.admin.features.faq.presentation.dto

import dev.bytepride.truprobackend.core.domain.model.PlatformRole

data class UpdateFAQData(
    val title: String?,
    val description: String?
)