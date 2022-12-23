package dev.december.jeterbackend.stream.signaler.presentation.dto.impl

import com.fasterxml.jackson.annotation.JsonProperty
import dev.december.jeterbackend.stream.signaler.presentation.dto.Negotiation

data class ToggleMuteNegotiationData(
    @JsonProperty("appointment_id")
    override val appointmentId: String,
    override val id: String,
    val muted: Boolean,
) : Negotiation
