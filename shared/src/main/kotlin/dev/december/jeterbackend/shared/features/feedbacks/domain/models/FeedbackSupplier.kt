package dev.december.jeterbackend.shared.features.feedbacks.domain.models

import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import java.time.LocalDateTime

data class FeedbackSupplier(
    val id: String,
    val grade: Float,
    val description: String,
    val createdAt: LocalDateTime,
    val client: Client,
)