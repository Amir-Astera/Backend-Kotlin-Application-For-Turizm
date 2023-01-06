package dev.december.jeterbackend.shared.features.clients.domain.models

import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.Gender
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import java.time.LocalDate

data class ClientUser(
    val id: String,
    val sessionCount: Int?,
    val expenses: Float?,
    val avatar: FileEntity?,
    val fullName: String?,
    val birthDate: LocalDate?,
    var gender: Gender?
)

data class UserClient(
    val id: String,
    val sessionCount: Int?,
    val expenses: Float?,
    val enableStatus: AccountEnableStatus?,
    val avatar: FileEntity?,
)