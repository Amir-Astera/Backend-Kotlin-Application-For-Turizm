package dev.december.jeterbackend.client.features.appointments.presentation.dto

import dev.december.jeterbackend.shared.features.appointments.domain.models.CommunicationType
import java.time.LocalDateTime

data class CreateAppointmentData(
    val reservationDate: LocalDateTime,
    val communicationType: CommunicationType,
    val description: String,
    val supplierId: String,
)
