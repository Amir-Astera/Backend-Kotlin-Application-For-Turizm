package dev.december.jeterbackend.shared.features.suppliers.domain.models

import dev.december.jeterbackend.shared.core.domain.model.*
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.domain.models.*
import java.time.LocalDate
import java.time.LocalDateTime


data class Supplier(
    val id: String,
    val name: String?,
    val surName: String?,
    val patronymic: String?,
    val birthDate: LocalDate?,
    val passportFiles: List<File> = emptyList(),
    val avatar: File?,
    val education: Set<SupplierEducationResponse>?,
    val certificate: Set<SupplierCertificateResponse>?,
    val socialMedias: SupplierSocialMedia?,
    val bankAccount: SupplierBankAccount?,
    val userGender: UserGender,
    val file: File?,
    val enableStatus: AccountEnableStatus,
    val activityStatus: AccountActivityStatus,
    val status: SupplierStatus,
    val description: String,
    val rating: Float,
    val feedbackCount: Int,
    val clientSize: Int,
    val sessionCount: Int,
//    val profession: Profession? = null,
    val experience: Int?,
    val files: List<File> = emptyList(),
    val phoneType: String?,
    val osType: OsType?,
    val appType: AppType?,
    val audioPerHour: Int?,
    val videoPerHour: Int?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
//    val user: User?,
    val notify: Boolean,
    val registrationToken: String?,
    val isFavorite: Boolean? = null
)
