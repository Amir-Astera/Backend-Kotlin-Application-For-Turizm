package dev.december.jeterbackend.shared.features.suppliers.domain.models

import dev.december.jeterbackend.shared.core.domain.model.*
import dev.december.jeterbackend.shared.features.files.domain.models.File
import java.time.LocalDate
import java.time.LocalDateTime


data class Supplier(
    val id: String,
    val phone: String,
    val email: String,
    val name: String?,
    val surname: String?,
    val patronymic: String?,
    val birthDate: LocalDate?,
    val passportFiles: List<File> = emptyList(),
    val avatar: File?,
    val education: Set<SupplierEducationResponse>?,
    val certificate: Set<SupplierCertificateResponse>?,
    val socialMedias: SupplierSocialMedia?,
    val bankAccount: SupplierBankAccount?,
    val userGender: Gender,
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
    val osType: OsType?,
    val audioPerHour: Int?,
    val videoPerHour: Int?,
    val notify: Boolean,
    val registrationToken: String?,
    val isFavorite: Boolean? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
