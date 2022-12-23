package dev.december.jeterbackend.client.features.tour.domain.services

import dev.december.jeterbackend.client.features.tour.domain.model.ClientTour
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.tours.domain.models.CommunicationType
import java.time.LocalDateTime

interface TourService {
    suspend fun create(
        reservationDate: LocalDateTime,
        communicationType: CommunicationType,
        description: String,
        userId: String,
        supplierId: String,
    ): Data<String>

    suspend fun get(id: String, appointmentId: String): Data<ClientTour>
//    suspend fun getAll(id: String,
//                       statuses: Set<AppointmentStatus>,
//                       reservationDateFrom: LocalDateTime,
//                       reservationDateTo: LocalDateTime
//    ): Data<Map<LocalDate, List<ClientAppointment>>>

//    suspend fun getAllByClientAndSupplier(clientId: String,
//                                          supplierId: String
//    ): Data<Map<LocalDate, List<ClientAppointment>>>

    suspend fun delete(id: String): Data<Unit>
    suspend fun confirm(id: String): Data<String>
    suspend fun cancel(id: String): Data<String>
    suspend fun complete(id: String): Data<String>

    suspend fun suggestAnotherTime(
        id: String,
        reservationDate: LocalDateTime
    ): Data<String>
}
