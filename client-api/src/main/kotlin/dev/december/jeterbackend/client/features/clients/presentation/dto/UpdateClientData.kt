package dev.december.jeterbackend.client.features.clients.presentation.dto

import dev.december.jeterbackend.shared.core.domain.model.UserGender
import dev.december.jeterbackend.shared.features.files.domain.models.File
import java.time.LocalDate

data class UpdateClientData(
    val avatar: File?,
    val fullName: String?,
    val birthDate: LocalDate?,
    val gender: UserGender?,
//    val phone: String?,
//    val email: String?
)