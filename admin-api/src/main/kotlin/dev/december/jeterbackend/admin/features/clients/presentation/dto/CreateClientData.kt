package dev.december.jeterbackend.admin.features.clients.presentation.dto

import dev.december.jeterbackend.shared.core.domain.model.Gender
import java.time.LocalDate

data class CreateClientData(
    val fullName: String,
    val birthDate: LocalDate?,
    val gender: Gender? = Gender.UNKNOWN,
    val avatarId: String?,
    val registrationToken: String
)