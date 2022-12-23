package dev.december.jeterbackend.supplier.features.appointments.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.appointments.presentation.dto.FreeTimeDto
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import org.springframework.data.domain.Page
import java.time.LocalDate
import java.time.LocalDateTime

interface AppointmentService {
    suspend fun get(id: String, appointmentId: String): Data<Unit>//Appointment
    suspend fun getAll(id: String,
                       statuses: Set<AppointmentStatus>,
                       reservationDateFrom: LocalDateTime,
                       reservationDateTo: LocalDateTime
    ): Data<Unit>//Map<LocalDate, List<Appointment>>
    suspend fun delete(id: String): Data<Unit>
    suspend fun confirm(id: String): Data<String>
    suspend fun cancel(id: String): Data<String>
    suspend fun complete(id: String): Data<String>
    suspend fun suggestAnotherTime(
        userId: String,
        id: String,
        reservationDate: LocalDateTime
    ): Data<String>

    suspend fun getSupplierFreeTime(
        userId: String,
        date: LocalDate
    ): Data<Unit>//List<FreeTimeDto>

    suspend fun getListOfNotConfirmedAppointments(
        userId: String,
        page: Int,
        size: Int,
    ): Data<Unit>//Page<Appointment>
}
