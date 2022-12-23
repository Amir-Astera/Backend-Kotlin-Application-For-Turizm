package dev.december.jeterbackend.shared.features.suppliers.domain.models

import dev.december.jeterbackend.shared.features.files.domain.models.File
import java.time.LocalDate

data class SupplierCertificateUpdate(
    val title: String?,
    val issueDate: LocalDate?,
    val specialization: String?,
    val fileId: File?,
)