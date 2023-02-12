package dev.december.jeterbackend.shared.features.notifications.data.repositories

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.notifications.data.entities.NotificationEntity
import dev.december.jeterbackend.shared.features.notifications.data.entities.NotificationStatus
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface NotificationRepository  : CrudRepository<NotificationEntity, String> {
    fun deleteBySupplierAndClientAndAppointmentDatetime(
        supplier: SupplierEntity,
        clientEntity: ClientEntity,
        appointmentDatetime: LocalDateTime
    )

    fun findByStatusAndAppointmentDatetimeBetween(
        notificationStatus: NotificationStatus,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<NotificationEntity>

    fun deleteAllByStatus(status: NotificationStatus)

    fun findBySupplierAndClientAndAppointmentDatetime(
        supplier: SupplierEntity,
        clientEntity: ClientEntity,
        appointmentDatetime: LocalDateTime,
    ): NotificationEntity?
}