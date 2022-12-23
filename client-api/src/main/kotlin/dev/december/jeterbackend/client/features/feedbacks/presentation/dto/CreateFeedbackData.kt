package dev.december.jeterbackend.client.features.feedbacks.presentation.dto

data class CreateFeedbackData(
    val supplierId: String,
    val grade: Float,
    val description: String?,
)
