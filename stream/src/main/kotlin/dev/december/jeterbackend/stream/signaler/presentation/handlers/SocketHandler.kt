package dev.december.jeterbackend.stream.signaler.presentation.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.december.jeterbackend.stream.signaler.errors.ActionNotAllowedFailure
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import dev.december.jeterbackend.shared.core.errors.Failure
import dev.december.jeterbackend.stream.signaler.data.entities.ParamEntity
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.stream.signaler.domain.usecases.*
import dev.december.jeterbackend.stream.signaler.presentation.dto.Negotiation
import dev.december.jeterbackend.stream.signaler.presentation.dto.PeerInfo
import dev.december.jeterbackend.stream.signaler.presentation.dto.Request
import dev.december.jeterbackend.stream.signaler.presentation.dto.RequestType
import dev.december.jeterbackend.stream.signaler.presentation.dto.impl.*

@Component
class SocketHandler(
    private val objectMapper: ObjectMapper,
    private val addPeerUseCase: AddPeerUseCase,
    private val offerUseCase: OfferUseCase,
    private val answerUseCase: AnswerUseCase,
    private val candidateUseCase: CandidateUseCase,
    private val toggleMuteUseCase: ToggleMuteUseCase,
    private val keepAliveUseCase: KeepAliveUseCase,
    private val hangupUseCase: HangupUseCase,
    private val leaveUseCase: LeaveUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : TextWebSocketHandler() {
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val request: RequestData = objectMapper.readValue(message.payload)
        val result =
            when (request.type) {
                RequestType.NEW -> addPeerUseCase(prepareParam<PeerInfoData, PeerInfo>(session, request))
                RequestType.LEAVE -> hangupUseCase(prepareParam(session, request))
                RequestType.OFFER -> offerUseCase(prepareParam<OfferNegotiationData, Negotiation>(session, request))
                RequestType.ANSWER -> answerUseCase(prepareParam<AnswerNegotiationData, Negotiation>(session, request))
                RequestType.CANDIDATE -> candidateUseCase(
                    prepareParam<CandidateNegotiationData, Negotiation>(
                        session,
                        request
                    )
                )
                RequestType.KEEPALIVE -> keepAliveUseCase(prepareParam(session, request))
                RequestType.START -> Data.Error(ActionNotAllowedFailure())
                RequestType.EXPIRED -> Data.Error(ActionNotAllowedFailure())
                RequestType.ERROR -> Data.Error(ActionNotAllowedFailure())
                RequestType.TOGGLE_MUTE -> toggleMuteUseCase(
                    prepareParam<ToggleMuteNegotiationData, Negotiation>(
                        session,
                        request
                    )
                )
            }
        processResult(result, session, request)
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
        keepAliveUseCase(ParamEntity(session, Unit))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        super.afterConnectionClosed(session, status)
        leaveUseCase(ParamEntity(session, Unit))
    }

    private inline fun <reified SubType : Type, Type> prepareParam(
        session: WebSocketSession,
        request: Request
    ): ParamEntity<Type> {
        val data: SubType = objectMapper.readValue(objectMapper.writeValueAsString(request.data))
        return ParamEntity(
            session = session,
            data = data,
        )
    }

    private fun prepareError(session: WebSocketSession, request: Request, failure: Failure): ParamEntity<Request> {
        val error = ErrorData(
            requestType = request.type,
            reason = failure.message,
        )
        val data = objectMapper.writeValueAsString(error)
        val requestData = RequestData(
            type = RequestType.ERROR,
            data = data
        )
        return ParamEntity(
            session = session,
            data = requestData
        )
    }

    private fun processResult(result: Data<Unit>, session: WebSocketSession, request: Request) {
        if (result is Data.Error) {
            sendMessageUseCase(prepareError(session, request, result.failure))
        }
    }
}