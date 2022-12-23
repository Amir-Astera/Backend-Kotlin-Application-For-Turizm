package dev.december.jeterbackend.shared.features.feedbacks.domain.models

import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import java.time.LocalDateTime

data class FeedbackClient(
    val id: String,
    val grade: Float,
    val description: String,
    val createdAt: LocalDateTime,
    val status: FeedbackStatus,
    val supplier: Supplier,
)