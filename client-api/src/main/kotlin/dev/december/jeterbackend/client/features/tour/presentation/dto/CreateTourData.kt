package dev.december.jeterbackend.client.features.tour.presentation.dto

import dev.december.jeterbackend.shared.features.tours.domain.models.CommunicationType
import java.time.LocalDateTime

data class CreateTourData(
    val reservationDate: LocalDateTime,
    val communicationType: CommunicationType,
    val description: String,
    val supplierId: String,
)
