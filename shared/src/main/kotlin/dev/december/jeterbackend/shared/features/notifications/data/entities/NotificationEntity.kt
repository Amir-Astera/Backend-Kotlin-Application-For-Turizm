package dev.december.jeterbackend.shared.features.notifications.data.entities

import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "notification")
data class NotificationEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    val supplier: SupplierEntity,
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    val client: ClientEntity,
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: NotificationStatus = NotificationStatus.NOT_SENT,
    @Column(name = "appointment_datetime", nullable = false)
    val appointmentDatetime: LocalDateTime,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)