package dev.december.jeterbackend.shared.features.clients.domain.models

import dev.december.jeterbackend.shared.core.domain.model.*
import dev.december.jeterbackend.shared.features.files.domain.models.File
import java.time.LocalDate
import java.time.LocalDateTime

data class Client(
    val id: String,
    val fullName: String?,
    val userGender: UserGender,
    val file: File?,
    val birthDate: LocalDate?,
    val sessionCount: Int,
    val expenses: Float,
    val enableStatus: AccountEnableStatus,
    val activityStatus: AccountActivityStatus,
    val avatar: File?,
    val phoneType: String?,
    val osType: OsType?,
    val appType: AppType?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val registrationToken: String?,
)
