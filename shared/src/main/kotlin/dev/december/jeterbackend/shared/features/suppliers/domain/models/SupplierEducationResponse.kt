package dev.december.jeterbackend.shared.features.suppliers.domain.models

import dev.december.jeterbackend.shared.features.files.domain.models.File

data class SupplierEducationResponse(
    val id: String,
    val institutionName: String?,
    val graduationYear: Int?,
    val faculty: String?,
    val fileId: File?,
)