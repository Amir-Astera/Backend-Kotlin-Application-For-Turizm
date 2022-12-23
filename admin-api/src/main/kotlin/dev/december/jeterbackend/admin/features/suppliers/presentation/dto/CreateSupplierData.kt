package dev.bytepride.truprobackend.admin.features.suppliers.presentation.dto

import dev.bytepride.truprobackend.features.files.domain.models.File
import dev.bytepride.truprobackend.features.suppliers.domain.models.*

data class CreateSupplierData(
    val email: String,
    val password: String,
    val generalInfo: SupplierGeneralInfo,
    val education: SupplierEducation?,
    val schedule: SupplierSchedule?,
    val socialMedia: SupplierSocialMedia?,
    val price: SupplierPrice?,
    val files: List<File>?,
    val registrationToken: String
)