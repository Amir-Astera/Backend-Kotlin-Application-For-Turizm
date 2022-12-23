package dev.december.jeterbackend.client.features.tour.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.tour.domain.services.TourService
import org.springframework.stereotype.Component


@Component
class ConfirmTourUseCase (
    private val tourService: TourService
) : UseCase<ConfirmTourParams, String> {
    override suspend fun invoke(params: ConfirmTourParams): Data<String> {
        return tourService.confirm(params.id)
    }
}

data class ConfirmTourParams(
    val id: String
)