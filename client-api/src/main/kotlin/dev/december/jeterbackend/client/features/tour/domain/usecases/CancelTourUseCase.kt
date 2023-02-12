package dev.december.jeterbackend.client.features.tour.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.tour.domain.services.TourService
import org.springframework.stereotype.Component


//@Component
//class CancelTourUseCase(
//    private val tourService: TourService
//) : UseCase<CancelTourParams, String> {
//    override suspend fun invoke(params: CancelTourParams): Data<String> {
//        return tourService.cancel(params.id)
//    }
//}
//
//data class CancelTourParams(
//    val id: String,
//)