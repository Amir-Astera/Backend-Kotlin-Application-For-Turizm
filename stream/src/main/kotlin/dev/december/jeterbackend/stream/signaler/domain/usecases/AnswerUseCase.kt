package dev.december.jeterbackend.stream.signaler.domain.usecases

import org.springframework.stereotype.Component
import dev.december.jeterbackend.stream.core.usecases.UseCase
import dev.december.jeterbackend.stream.signaler.data.entities.ParamEntity
import dev.december.jeterbackend.stream.signaler.domain.services.PeerService
import dev.december.jeterbackend.stream.signaler.presentation.dto.Negotiation

@Component
class AnswerUseCase(
    private val peerService: PeerService
) : UseCase<Unit, ParamEntity<Negotiation>> {
  override fun invoke(param: ParamEntity<Negotiation>) = peerService.answer(param)
}