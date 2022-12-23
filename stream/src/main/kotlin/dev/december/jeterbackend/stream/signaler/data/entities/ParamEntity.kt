package dev.december.jeterbackend.stream.signaler.data.entities

import org.springframework.web.socket.WebSocketSession

data class ParamEntity<Data>(
    val session: WebSocketSession,
    val data: Data
)