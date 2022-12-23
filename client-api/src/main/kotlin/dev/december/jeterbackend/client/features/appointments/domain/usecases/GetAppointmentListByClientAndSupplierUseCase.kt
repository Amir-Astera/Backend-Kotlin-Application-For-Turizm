package dev.december.jeterbackend.client.features.appointments.domain.usecases

import dev.december.jeterbackend.client.features.appointments.domain.model.ClientAppointment
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.appointments.domain.services.AppointmentService
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class GetAppointmentListByClientAndSupplierUseCase (
    private val appointmentService: AppointmentService
) : UseCase<GetAppointmentListByClientAndSupplierParams, Map<LocalDate, List<ClientAppointment>>> {
    override suspend fun invoke(
        params: GetAppointmentListByClientAndSupplierParams
    ): Data<Map<LocalDate, List<ClientAppointment>>> {
        return appointmentService.getAllByClientAndSupplier(
            params.clientId,
            params.supplierId
        )
    }
}

data class GetAppointmentListByClientAndSupplierParams(
    val clientId: String,
    val supplierId: String
)