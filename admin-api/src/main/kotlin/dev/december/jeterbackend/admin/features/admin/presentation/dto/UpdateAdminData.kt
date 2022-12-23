package dev.december.jeterbackend.admin.features.admin.presentation.dto

import dev.december.jeterbackend.shared.core.domain.model.UserGender
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminAuthorityCode
import java.time.LocalDate

data class UpdateAdminData(
    val avatarId: String?,
    val fullName: String?,
    val birthDate: LocalDate?,
    val gender: UserGender?,
    val authority: AdminAuthorityCode?
)