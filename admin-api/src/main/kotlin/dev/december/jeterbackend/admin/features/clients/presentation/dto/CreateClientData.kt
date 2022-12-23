package dev.december.jeterbackend.admin.features.clients.presentation.dto

import dev.december.jeterbackend.shared.core.domain.model.UserGender
import java.time.LocalDate

data class CreateClientData(
    val fullName: String,
    val birthDate: LocalDate?,
    val gender: UserGender? = UserGender.UNKNOWN,
    val avatarId: String?,
    val registrationToken: String
)