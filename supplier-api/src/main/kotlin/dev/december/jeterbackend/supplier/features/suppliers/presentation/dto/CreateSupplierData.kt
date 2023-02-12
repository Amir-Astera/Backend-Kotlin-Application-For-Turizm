package dev.december.jeterbackend.supplier.features.suppliers.presentation.dto

import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.domain.models.*
import dev.december.jeterbackend.shared.features.tours.domain.models.SupplierPrice
import java.time.LocalDate

data class CreateSupplierData(
    val email: String,
    val phone: String,
    val password: String,
    val generalInfo: SupplierGeneralInfo?,
    val education: List<SupplierEducation>?,
    val certificate: List<SupplierCertificate>?,
    val bankAccount: SupplierBankAccount?,
    val socialMedia: SupplierSocialMedia?,
    val avatar: File?,
    val schedule: SupplierSchedule?,
    val price: SupplierPrice?,
    val files: List<File>?,
    val registrationToken: String?,
    val experience: LocalDate?
)