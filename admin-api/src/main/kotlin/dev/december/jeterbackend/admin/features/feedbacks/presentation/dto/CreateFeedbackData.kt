package dev.december.jeterbackend.admin.features.feedbacks.presentation.dto

data class CreateFeedbackData(
    val grade: Float,
    val description: String?,
    val clientId: String,
    val supplierId: String,
)
