package dev.december.jeterbackend.shared.features.tours.domain.models

import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.files.domain.models.File
import java.time.LocalDateTime

//TODO Надо создать изменить таблицу календарь и создать новую для время,
data class Tour(
    val id: String,
    val reservationDate: LocalDateTime = LocalDateTime.now(),
    val oldReservationDate: LocalDateTime?,
    val communicationType: CommunicationType,
    val tourStatus: TourStatus,
    val description: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val client: Client,
    val supplier: Supplier,
    val payment: Int?,
    val receipt: File?
) {
    override fun toString(): String {
        return "Appointment(id='$id', client=${client.id}, supplier=${supplier.id}, reservationDate=$reservationDate, communicationType=$communicationType, appointmentStatus=$tourStatus, description='$description', createdAt=$createdAt)"
    }
}

