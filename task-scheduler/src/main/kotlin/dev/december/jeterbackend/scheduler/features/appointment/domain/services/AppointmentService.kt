package dev.december.jeterbackend.scheduler.features.appointment.domain.services

import dev.december.jeterbackend.shared.core.results.Data

interface AppointmentService {
    suspend fun cancelOld(): Data<Unit>
}