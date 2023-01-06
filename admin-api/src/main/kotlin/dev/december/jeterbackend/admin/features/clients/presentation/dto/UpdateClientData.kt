package dev.december.jeterbackend.admin.features.clients.presentation.dto

import dev.december.jeterbackend.shared.core.domain.model.Gender
import java.time.LocalDate

data class UpdateClientData(
    val sessionCount: Int?,
    val expenses: Float?,
    val avatarId: String?,
    val fullName: String?,
    val birthDate: LocalDate?,
    val gender: Gender?,
    val phone: String?
)