package dev.december.jeterbackend.supplier.features.tour.domain.usecases

import dev.december.jeterbackend.supplier.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.tours.domain.models.Tour
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

//@Component
//class GetNotConfirmedTourListUseCase(
//    private val tourService: TourService
//) : UseCase<GetNotConfirmedTourListParams, Page<Appointment>> {
//    override suspend fun invoke(params: GetNotConfirmedTourListParams): Data<Page<Appointment>> {
//        return tourService.getListOfNotConfirmedAppointments(
//            params.userId,
//            params.page,
//            params.size
//        )
//    }
//}
//
//data class GetNotConfirmedTourListParams(
//    val userId: String,
//    val page: Int,
//    val size: Int,
//)