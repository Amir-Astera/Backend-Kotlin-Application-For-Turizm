package dev.december.jeterbackend.client.features.appointments.domain.model

import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.appointments.domain.models.CommunicationType
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import java.time.LocalDateTime

data class ClientAppointment(
    val id: String,
    val reservationDate: LocalDateTime = LocalDateTime.now(),
    val oldReservationDate: LocalDateTime?,
    val communicationType: CommunicationType,
    val appointmentStatus: AppointmentStatus,
    val description: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val supplier: Supplier,
    val payment: Int?,
    val receipt: File?
)