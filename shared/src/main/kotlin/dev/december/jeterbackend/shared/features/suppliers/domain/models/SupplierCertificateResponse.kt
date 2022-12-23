package dev.december.jeterbackend.shared.features.suppliers.domain.models

import dev.december.jeterbackend.shared.features.files.domain.models.File
import java.time.LocalDate

data class SupplierCertificateResponse(
    val id: String,
    val title: String?,
    val issueDate: LocalDate?,
    val specialization: String?,
    val file: File?,
)
