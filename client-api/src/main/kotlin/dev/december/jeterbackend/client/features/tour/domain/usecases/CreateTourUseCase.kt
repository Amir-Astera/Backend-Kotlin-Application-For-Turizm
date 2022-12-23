package dev.december.jeterbackend.client.features.tour.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.features.tours.domain.models.CommunicationType
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CreateTourUseCase(
    private val tourService: TourService
) : UseCase<CreateTourParams, String> {
    override suspend fun invoke(params: CreateTourParams): Data<String> {
        return tourService.create(
            params.reservationDate,
            params.communicationType,
            params.description,
            params.userId,
            params.supplierId,

        )
    }
}

data class CreateTourParams(
    val reservationDate: LocalDateTime,
    val communicationType: CommunicationType,
    val description: String,
    val userId: String,
    val supplierId: String,
)