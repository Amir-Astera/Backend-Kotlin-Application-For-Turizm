package dev.december.jeterbackend.stream.signaler.presentation.dto

interface PeerInfo: Negotiation {
  override val appointmentId: String
  override val id: String
}
