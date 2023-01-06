package dev.december.jeterbackend.admin.features.admin.presentation.dto

import dev.december.jeterbackend.shared.core.domain.model.Gender
import java.time.LocalDate

data class UpdateAdminByUserIdData(
    val avatarId: String?,
    val fullName: String?,
    val birthDate: LocalDate?,
    val gender: Gender?
)