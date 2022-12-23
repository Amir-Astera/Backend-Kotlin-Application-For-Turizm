package dev.december.jeterbackend.stream.signaler.domain.usecases

import org.springframework.stereotype.Component
import dev.december.jeterbackend.stream.core.usecases.UseCase
import dev.december.jeterbackend.stream.signaler.data.entities.ParamEntity
import dev.december.jeterbackend.stream.signaler.domain.services.PeerService
import dev.december.jeterbackend.stream.signaler.presentation.dto.PeerInfo

@Component
class AddPeerUseCase(
    private val peerService: PeerService
) : UseCase<Unit, ParamEntity<PeerInfo>> {
  override fun invoke(param: ParamEntity<PeerInfo>) = peerService.addPeer(param)
}