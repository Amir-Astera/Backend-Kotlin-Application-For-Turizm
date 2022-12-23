package dev.december.jeterbackend.shared.features.feedbacks.domain.models

import dev.december.jeterbackend.shared.features.feedbacks.domain.models.Feedback
import org.springframework.data.domain.Page

data class FeedbackWithGrade(
    val averageGrade: Double,
    val feedbacks: Page<Feedback>
)