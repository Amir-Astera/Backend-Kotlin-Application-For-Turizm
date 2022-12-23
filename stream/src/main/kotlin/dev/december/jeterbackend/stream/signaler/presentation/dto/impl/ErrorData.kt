package dev.december.jeterbackend.stream.signaler.presentation.dto.impl

import com.fasterxml.jackson.annotation.JsonProperty
import dev.december.jeterbackend.stream.signaler.presentation.dto.Error
import dev.december.jeterbackend.stream.signaler.presentation.dto.RequestType

data class ErrorData(
    @JsonProperty("request")
    override val requestType: RequestType,
    override val reason: String,
) : Error