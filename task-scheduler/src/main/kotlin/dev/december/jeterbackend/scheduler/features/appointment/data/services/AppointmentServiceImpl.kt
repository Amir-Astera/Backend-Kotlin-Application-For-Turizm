package dev.december.jeterbackend.scheduler.features.appointment.data.services

import dev.december.jeterbackend.scheduler.features.appointment.domain.errors.AppointmentCancelFailure
import dev.december.jeterbackend.scheduler.features.appointment.domain.services.AppointmentService
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.data.repositories.AppointmentRepository
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AppointmentServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val appointmentRepository: AppointmentRepository,
): AppointmentService {
    override suspend fun cancelOld(): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val reservationDateTo = LocalDateTime.now().minusSeconds(1)
                val appointmentsToCancel = appointmentRepository.findAllByReservationDateBeforeAndAppointmentStatusIn(
                    reservationDateTo, setOf(AppointmentStatus.CLIENT_SUBMITTED, AppointmentStatus.SUPPLIER_SUBMITTED, AppointmentStatus.CONFIRMED)
                )
                val updatedAppointments = appointmentsToCancel.map { appointment ->
                    appointment.copy(
                        appointmentStatus = AppointmentStatus.CANCELED,
                    )
                }
                appointmentRepository.saveAll(updatedAppointments)
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            println(e)
            Data.Error(AppointmentCancelFailure())
        }
    }
}