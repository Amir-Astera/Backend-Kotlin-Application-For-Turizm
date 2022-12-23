package dev.december.jeterbackend.stream.signaler.presentation.dto.impl

import com.fasterxml.jackson.annotation.JsonRawValue
import dev.december.jeterbackend.stream.signaler.presentation.dto.Request
import dev.december.jeterbackend.stream.signaler.presentation.dto.RequestType

data class RequestData(
    override val type: RequestType,
    @JsonRawValue
    override val data: Any?
) : Request

fun Request.toData(): Request {
    return RequestData(
        type = type,
        data = data,
    )
}