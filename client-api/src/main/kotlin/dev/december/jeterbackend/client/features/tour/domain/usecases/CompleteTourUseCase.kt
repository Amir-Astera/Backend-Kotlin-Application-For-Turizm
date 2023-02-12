package dev.december.jeterbackend.client.features.tour.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.tour.domain.services.TourService
import org.springframework.stereotype.Component



//@Component
//class CompleteTourUseCase (
//    private val tourService: TourService
//) : UseCase<CompleteTourParams, String> {
//    override suspend fun invoke(params: CompleteTourParams): Data<String> {
//        return tourService.complete(params.id)
//    }
//}
//
//data class CompleteTourParams(
//    val id: String,
//)