package dev.december.jeterbackend.stream.signaler.data.services

import com.fasterxml.jackson.databind.ObjectMapper
import dev.december.jeterbackend.stream.signaler.errors.AppointmentNotFoundFailure
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import dev.december.jeterbackend.stream.signaler.data.entities.ParamEntity
import dev.december.jeterbackend.stream.signaler.domain.services.PeerService
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.tours.data.repositories.TourRepository
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import dev.december.jeterbackend.stream.signaler.data.model.AppointmentModel
import dev.december.jeterbackend.stream.signaler.data.model.ClientModel
import dev.december.jeterbackend.stream.signaler.data.model.RequestModel
import dev.december.jeterbackend.stream.signaler.data.model.SupplierModel
import dev.december.jeterbackend.stream.signaler.presentation.dto.impl.toData
import dev.december.jeterbackend.stream.signaler.presentation.dto.Negotiation
import dev.december.jeterbackend.stream.signaler.presentation.dto.PeerInfo
import dev.december.jeterbackend.stream.signaler.presentation.dto.Request
import dev.december.jeterbackend.stream.signaler.presentation.dto.RequestType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.ConcurrentHashMap

@Service
class PeerServiceImpl(
    private val objectMapper: ObjectMapper,
    private val taskScheduler: ThreadPoolTaskScheduler,
    private val tourRepository: TourRepository,
) : PeerService {
    private val appointments: MutableMap<String, AppointmentModel> = ConcurrentHashMap()

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun offer(param: ParamEntity<Negotiation>) = sendNegotiation(RequestType.OFFER, param)

    override fun answer(param: ParamEntity<Negotiation>) = sendNegotiation(RequestType.ANSWER, param)

    override fun candidate(param: ParamEntity<Negotiation>) = sendNegotiation(RequestType.CANDIDATE, param)

    override fun toggleMute(param: ParamEntity<Negotiation>) = sendNegotiation(RequestType.TOGGLE_MUTE, param)

    override fun keepAlive(param: ParamEntity<Unit>): Data<Unit> {
        return sendRequest(param.session, RequestType.KEEPALIVE)
    }

    private fun sendNegotiation(type: RequestType, param: ParamEntity<Negotiation>): Data<Unit> {
        val negotiation = param.data
        val appointment = appointments[negotiation.appointmentId] ?: return Data.Error(AppointmentNotFoundFailure())
        val session = when (negotiation.id) {
            appointment.supplier?.id -> appointment.client?.session
            appointment.client?.id -> appointment.supplier?.session
            else -> return Data.Error(
                AppointmentNotFoundFailure()
            )
        } ?: return Data.Error(
            AppointmentNotFoundFailure()
        )
        val data = objectMapper.writeValueAsString(negotiation)
        return sendRequest(session, type, data)
    }

    private fun sendRequest(session: WebSocketSession, type: RequestType, data: String? = null): Data<Unit> {
        val request = RequestModel(
            type = type,
            data = data
        ).toData()
        val message = ParamEntity(session = session, data = request)
        return sendMessage(message)
    }

    override fun sendMessage(param: ParamEntity<Request>): Data<Unit> {
        return try {
            val session = param.session
            val text = objectMapper.writeValueAsString(param.data)
            session.sendMessage(TextMessage(text))
            Data.Success(Unit)
        } catch (e: Exception) {
            Data.Error(MessageSendFailure())
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun addPeer(param: ParamEntity<PeerInfo>): Data<Unit> {
        return try {
            val appointmentId = param.data.appointmentId
            val appointmentEntity =
                tourRepository.findByIdOrNull(appointmentId) ?: return Data.Error(AppointmentNotFoundFailure())
            if (appointmentEntity.tourStatus == TourStatus.COMPLETED) {
                return Data.Error(AppointmentCompletedFailure())
            }
            if (appointmentEntity.reservationDate.isAfter(LocalDateTime.now().plusMinutes(10)) ||
                appointmentEntity.tourStatus != TourStatus.CONFIRMED
            ) {
                return Data.Error(AppointmentTooEarlyFailure())
            }
            val appointment =
                appointments.getOrDefault(appointmentId, AppointmentModel(status = TourStatus.CONFIRMED)).run {
                    if (this.client != null && this.supplier != null) {
                        return Data.Error(AppointmentInProgressFailure())
                    }
                    when (param.data.id) {
                        appointmentEntity.client.id -> run {
//                        if (this.supplier != null) {
//                            firebaseMessaging.send(
//                                Message.builder()
//                                    .putData("type", NotificationType.APPOINTMENT_STARTED.toString().lowercase())
//                                    .setNotification(
//                                        Notification.builder()
//                                            .setBody(
//                                                "Your client is ready to start appointment!"
//                                            )
//                                            .build()
//                                    )
//                                    .setToken(appointmentEntity.supplier.recentRegistrationToken)
//                                    .build(),
//                            )
//                        }
                            this.copy(client = ClientModel(param.data.id, param.session))
                        }

                        appointmentEntity.supplier.id -> run {
//                        if (this.client != null) {
//                            firebaseMessaging.send(
//                                Message.builder()
//                                    .putData("type", NotificationType.APPOINTMENT_STARTED.toString().lowercase())
//                                    .putData("appointment_id", appointmentId)
//                                    .setNotification(
//                                        Notification.builder()
//                                            .setBody(
//                                                "Specialist is waiting for you in your appointment!"
//                                            )
//                                            .build()
//                                    )
//                                    .setToken(appointmentEntity.supplier.recentRegistrationToken)
//                                    .build(),
//                            )
//                        }
                            this.copy(supplier = SupplierModel(param.data.id, param.session))
                        }

                        else -> return Data.Error(
                            AppointmentNotFoundFailure()
                        )
                    }
                }
            appointments[appointmentId] = appointment
            if (appointment.client != null && appointment.supplier != null) {
                sendRequest(appointment.supplier.session, RequestType.START)
                //TODO add logic for reconnect
                //appointments[appointmentId] = appointment.copy(
                //    status = AppointmentStatus.IN_PROGRESS
                //)
                //appointmentRepository.save(appointmentEntity.copy(appointmentStatus = AppointmentStatus.IN_PROGRESS))
                val finishTimeInstant = appointmentEntity.reservationDate.plusHours(1).toInstant(ZoneOffset.UTC)
                taskScheduler.schedule(
                    { expired(appointmentId) }, finishTimeInstant
                )
            }
            Data.Success(Unit)
        } catch (e: Exception) {
            Data.Error(AddSessionFailure())
        }
    }

    private fun expired(appointmentId: String): Data<Unit> {
        val appointment = appointments[appointmentId] ?: return Data.Error(AppointmentNotFoundFailure())
        if (appointment.supplier != null && appointment.client != null) {
            sendRequest(appointment.supplier.session, RequestType.LEAVE)
            sendRequest(appointment.client.session, RequestType.LEAVE)
            tourRepository.findByIdOrNull(appointmentId)?.run{
                tourRepository.save(copy(tourStatus = TourStatus.COMPLETED))
            }
        }
        return Data.Success(Unit)
    }

    override fun leave(param: ParamEntity<Unit>, hangup: Boolean): Data<Unit> {
        for (key in appointments.keys) {
            val appointment = appointments[key]?.run {
                if (supplier?.session == param.session) {
                    copy(
                        supplier = null,
                    )
                } else if (client?.session == param.session) {
                    copy(
                        client = null,
                    )
                } else null
            } ?: continue
            if (hangup) {
                appointment.supplier?.run { sendRequest(session, RequestType.LEAVE) }
                appointment.client?.run { sendRequest(session, RequestType.LEAVE) }
            }
            if (appointment.supplier == null && appointment.client == null ) {
                appointments.remove(key)
            } else {
                appointments[key] = appointment
            }
            return Data.Success(Unit)
        }
        return Data.Error(PeerNotFoundFailure())
    }
}
