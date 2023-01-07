package dev.december.jeterbackend.client.features.tour.domain.model

import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import dev.december.jeterbackend.shared.features.tours.domain.models.CommunicationType
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import java.time.LocalDateTime

data class ClientTour(
    val id: String,
    val reservationDate: LocalDateTime = LocalDateTime.now(),
    val oldReservationDate: LocalDateTime?,
    val communicationType: CommunicationType,
    val tourStatus: TourStatus,
    val description: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val supplier: Supplier,
    val payment: Int?,
    val receipt: File?
)