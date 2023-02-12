package dev.december.jeterbackend.scheduler.features.notifications.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import java.time.LocalDateTime

interface NotificationService {
    suspend fun soonAppointment(): Data<Unit>;

    suspend fun clear()
}