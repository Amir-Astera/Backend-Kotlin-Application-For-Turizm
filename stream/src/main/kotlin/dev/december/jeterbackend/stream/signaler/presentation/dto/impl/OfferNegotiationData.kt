package dev.december.jeterbackend.stream.signaler.presentation.dto.impl

import com.fasterxml.jackson.annotation.JsonProperty
import dev.december.jeterbackend.stream.signaler.presentation.dto.Negotiation

data class OfferNegotiationData(
    @JsonProperty("appointment_id")
    override val appointmentId: String,
    override val id: String,
    val media: String,
    val description: Any,
) : Negotiation
