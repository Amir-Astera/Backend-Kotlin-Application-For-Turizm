package dev.december.jeterbackend.client.features.tour.domain.usecases

import dev.december.jeterbackend.client.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SuggestAnotherTimeUseCase (
    private val tourService: TourService
) : UseCase<SuggestAnotherTimeParams, String> {
    override suspend fun invoke(params: SuggestAnotherTimeParams): Data<String> {
        return tourService.suggestAnotherTime(params.id, params.reservationDateTime)
    }
}

data class SuggestAnotherTimeParams(
    val id: String,
    val reservationDateTime: LocalDateTime
)