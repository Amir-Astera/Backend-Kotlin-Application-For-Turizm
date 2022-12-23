package dev.december.jeterbackend.stream.signaler.data.model

import org.springframework.web.socket.WebSocketSession
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

data class SupplierModel(
    val id: String,
    val session: WebSocketSession,
)