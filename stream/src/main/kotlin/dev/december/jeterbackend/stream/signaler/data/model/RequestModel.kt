package dev.december.jeterbackend.stream.signaler.data.model

import dev.december.jeterbackend.stream.signaler.presentation.dto.Request
import dev.december.jeterbackend.stream.signaler.presentation.dto.RequestType

data class RequestModel(
    override val type: RequestType,
    override val data: String?
): Request