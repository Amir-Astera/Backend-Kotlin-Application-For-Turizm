package dev.december.jeterbackend.shared.features.notifications.data.entities

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.features.notifications.data.entities.NotificationStatus
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

//id          VARCHAR(50)  NOT NULL,
//app_user_id     VARCHAR(255),
//senders_name VARCHAR(255) NOT NULL,
//recipient_type TEXT NOT NULL,
//status TEXT NOT NULL,
//appointment_datetime  TIMESTAMP WITHOUT TIME ZONE NOT NULL
//created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
//updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
//CONSTRAINT pk_notification PRIMARY KEY (id)

@Entity(name = "notification")
data class NotificationEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "senders_name", nullable = false)
    val sendersName: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type", nullable = false)
    val recipientType: PlatformRole,
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
