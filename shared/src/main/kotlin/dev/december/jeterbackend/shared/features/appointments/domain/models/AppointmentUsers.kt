package dev.december.jeterbackend.shared.features.appointments.domain.models

import dev.december.jeterbackend.shared.features.files.domain.models.File

data class AppointmentUser (
    val appUserId: String?,
    val userId: String?,
    val fullName: String? = null,
    val file: File?,
)