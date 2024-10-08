package dev.december.jeterbackend.shared.features.appointments.data.entities

import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.appointments.domain.models.CommunicationType
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "appointment")
data class AppointmentEntity(
    @Id
    @Column(name = "id", nullable = false, length = 50)
    val id: String = UUID.randomUUID().toString(),
    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    val supplier: SupplierEntity,
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    val client: ClientEntity,
    @Column(name = "reservationDate", nullable = false)
    val reservationDate: LocalDateTime,
    @Column(name = "old_reservation_date", nullable = true)
    val oldReservationDate: LocalDateTime? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val communicationType: CommunicationType,
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    val description: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val appointmentStatus: AppointmentStatus,
    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updatedAt", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "payment", nullable = true)
    val payment: Int? = null,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "receipt_id", referencedColumnName = "id")
    val receipt: FileEntity? = null,
) {
    override fun toString(): String {
        return "AppointmentEntity(id='$id', supplier=${supplier.id}, client=${client.id}, reservationDate=$reservationDate, communicationType=$communicationType, description='$description', appointmentStatus=$appointmentStatus, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}
