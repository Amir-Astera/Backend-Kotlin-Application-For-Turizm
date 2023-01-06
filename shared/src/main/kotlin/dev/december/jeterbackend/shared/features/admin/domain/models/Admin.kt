package dev.december.jeterbackend.shared.features.admin.domain.models

import dev.december.jeterbackend.shared.core.domain.model.*
import dev.december.jeterbackend.shared.features.files.domain.models.File
import java.time.LocalDate
import java.time.LocalDateTime

data class Admin(
    val id: String,
    val fullName: String,
    val gender: Gender,
    val file: File?,
    val birthDate: LocalDate?,
    val activityStatus: AccountActivityStatus,
    val enableStatus: AccountEnableStatus,
    val phoneType: String?,
    val osType: OsType?,
    val appType: AppType?,
    val haveUnreadMessages: Boolean?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)