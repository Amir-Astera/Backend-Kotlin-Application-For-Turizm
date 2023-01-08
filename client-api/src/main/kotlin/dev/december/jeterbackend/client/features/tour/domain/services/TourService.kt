package dev.december.jeterbackend.client.features.tour.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.tours.domain.models.CommunicationType
import dev.december.jeterbackend.shared.features.tours.domain.models.Tour
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import java.time.LocalDate
import java.time.LocalDateTime

interface TourService {
    suspend fun create(
        reservationDate: LocalDateTime,
        communicationType: CommunicationType,
        description: String,
        userId: String,
        supplierId: String,
    ): Data<String>

    suspend fun get(id: String, tourId: String): Data<Tour>//нужно возврощать какой то тур
    suspend fun getAll(id: String,
                       statuses: Set<TourStatus>,
                       reservationDateFrom: LocalDateTime,
                       reservationDateTo: LocalDateTime
    ): Data<List<Tour>>//Todo тут вместо localDate должен быть Tour

    suspend fun delete(id: String): Data<Unit>
//    suspend fun confirm(id: String): Data<String>
//    suspend fun cancel(id: String): Data<String>
//    suspend fun complete(id: String): Data<String>

//    suspend fun suggestAnotherTime(
//        id: String,
//        reservationDate: LocalDateTime
//    ): Data<String>
}
