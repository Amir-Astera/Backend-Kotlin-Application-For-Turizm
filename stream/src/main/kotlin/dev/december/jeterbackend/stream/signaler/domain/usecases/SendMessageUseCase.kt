package dev.december.jeterbackend.stream.signaler.domain.usecases

import org.springframework.stereotype.Component
import dev.december.jeterbackend.stream.core.usecases.UseCase
import dev.december.jeterbackend.stream.signaler.data.entities.ParamEntity
import dev.december.jeterbackend.stream.signaler.domain.services.PeerService
import dev.december.jeterbackend.stream.signaler.presentation.dto.Request

@Component
class SendMessageUseCase(
    private val peerService: PeerService
) : UseCase<Unit, ParamEntity<Request>> {
  override fun invoke(param: ParamEntity<Request>) = peerService.sendMessage(param)
}