package dev.december.jeterbackend.shared.features.appointments.domain.models

import com.fasterxml.jackson.annotation.JsonProperty

enum class CommunicationType{
    CHAT,
    AUDIO,
    VIDEO,
}