package dev.bytepride.truprobackend.admin.features.faq.presentation.dto

import dev.bytepride.truprobackend.core.domain.model.PlatformRole

data class CreateFAQData(
    val title: String,
    val description: String,
    val authority: PlatformRole
)