package dev.december.jeterbackend.shared.features.suppliers.domain.models

import dev.december.jeterbackend.shared.core.domain.model.UserGender
import dev.december.jeterbackend.shared.features.files.domain.models.File
import java.time.LocalDate

data class SupplierGeneralInfoUpdate(
    val name: String?,
    val surName: String?,
    val patronymic: String?,
    val birthDate: LocalDate?,
    val passportFiles: List<File>?,
    val avatar: File?,
    val experience: LocalDate?,
    val gender: UserGender?,
    val about: String?,
    val videoFileId: String?,
    val timeZone: String?,
    val specializationId: String?,
    val professionId: String?
)
