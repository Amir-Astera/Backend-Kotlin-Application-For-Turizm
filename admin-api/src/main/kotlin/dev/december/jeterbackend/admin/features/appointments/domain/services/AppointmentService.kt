package dev.december.jeterbackend.admin.features.appointments.domain.services

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentSortField
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.appointments.domain.models.CommunicationType
import org.springframework.data.domain.Page
import java.time.LocalDateTime

interface AppointmentService {
    suspend fun get(id: String, authority: PlatformRole, appointmentId: String): Data<Appointment>
    suspend fun getAll(id: String,
                       authority: PlatformRole,
                       communicationTypes: Set<CommunicationType>?,
                       statuses: Set<AppointmentStatus>?,
                       sortField: AppointmentSortField,
                       sortDirection: SortDirection,
                       page: Int,
                       size: Int,
                       createdFrom: LocalDateTime?,
                       createdTo: LocalDateTime?
    ): Data<Page<Appointment>>
}
