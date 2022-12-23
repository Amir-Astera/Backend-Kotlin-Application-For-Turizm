package dev.december.jeterbackend.stream.signaler.domain.services

import dev.december.jeterbackend.stream.signaler.data.entities.ParamEntity
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.stream.signaler.presentation.dto.Negotiation
import dev.december.jeterbackend.stream.signaler.presentation.dto.PeerInfo
import dev.december.jeterbackend.stream.signaler.presentation.dto.Request

interface PeerService {
  fun addPeer(param: ParamEntity<PeerInfo>): Data<Unit>
  fun offer(param: ParamEntity<Negotiation>): Data<Unit>
  fun answer(param: ParamEntity<Negotiation>): Data<Unit>
  fun candidate(param: ParamEntity<Negotiation>): Data<Unit>
  fun toggleMute(param: ParamEntity<Negotiation>): Data<Unit>
  fun keepAlive(param: ParamEntity<Unit>): Data<Unit>
  fun leave(param: ParamEntity<Unit>, hangup: Boolean = false): Data<Unit>
  fun sendMessage(param: ParamEntity<Request>): Data<Unit>
}