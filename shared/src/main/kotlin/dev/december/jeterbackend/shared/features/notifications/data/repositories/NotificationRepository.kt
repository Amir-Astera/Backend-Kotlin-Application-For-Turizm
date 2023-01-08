package dev.december.jeterbackend.shared.features.notifications.data.repositories

import dev.december.jeterbackend.shared.features.notifications.data.entities.NotificationEntity
import dev.december.jeterbackend.shared.features.notifications.data.entities.NotificationStatus
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface NotificationRepository  : CrudRepository<NotificationEntity, String> {
    fun findByStatusAndRecipientTypeAndAppointmentDatetimeBetween(
        notificationStatus: NotificationStatus,
        recipientType: PlatformRole,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<NotificationEntity>

    fun deleteAllByStatusAndRecipientType(status: NotificationStatus, recipientType: PlatformRole)
}