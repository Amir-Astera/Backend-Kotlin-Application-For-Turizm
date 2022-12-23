package dev.december.jeterbackend.shared.features.appointments.domain.models

import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import java.time.LocalDateTime


data class Appointment(
    val id: String,
    val reservationDate: LocalDateTime = LocalDateTime.now(),
    val oldReservationDate: LocalDateTime?,
    val communicationType: CommunicationType,
    val appointmentStatus: AppointmentStatus,
    val description: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val client: Client,
    val supplier: Supplier,
    val payment: Int?,
    val receipt: File?
) {
    override fun toString(): String {
        return "Appointment(id='$id', reservationDate=$reservationDate, communicationType=$communicationType, appointmentStatus=$appointmentStatus, description='$description', createdAt=$createdAt, client=${client.id}, supplier=${supplier.id})"
    }
}

