package dev.december.jeterbackend.stream.signaler.domain.usecases

import org.springframework.stereotype.Component
import dev.december.jeterbackend.stream.core.usecases.UseCase
import dev.december.jeterbackend.stream.signaler.data.entities.ParamEntity
import dev.december.jeterbackend.stream.signaler.domain.services.PeerService

@Component
class KeepAliveUseCase(
    private val peerService: PeerService
) : UseCase<Unit, ParamEntity<Unit>> {
  override fun invoke(param: ParamEntity<Unit>) = peerService.keepAlive(param)
}