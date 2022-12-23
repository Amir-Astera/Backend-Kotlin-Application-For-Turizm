package dev.december.jeterbackend.admin.features.admin.presentation.dto

import dev.december.jeterbackend.shared.core.domain.model.UserGender
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminAuthorityCode
import java.time.LocalDate

data class CreateAdminData(
    val fullName: String,
    val gender: UserGender? = UserGender.UNKNOWN,
    val email: String,
    val phone: String,
    val password: String,
    val avatarId: String?,
    val birthDate: LocalDate?,
    val authorityCode: AdminAuthorityCode
)