package dev.december.jeterbackend.supplier.features.tour.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.tours.domain.models.Tour
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import dev.december.jeterbackend.supplier.features.tour.presentation.dto.FreeTimeDto
import org.springframework.data.domain.Page
import java.time.LocalDate
import java.time.LocalDateTime

interface TourService {
    suspend fun get(id: String, appointmentId: String): Data<Tour>
    suspend fun getAll(id: String,
                       statuses: Set<TourStatus>,
                       reservationDateFrom: LocalDateTime,
                       reservationDateTo: LocalDateTime
    ): Data<Unit>//Map<LocalDate, List<Tour>>
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
    ): Data<Unit>//Page<Tour>
}
