package dev.december.jeterbackend.supplier.features.feedbacks.presentation.dto

data class CreateFeedbackData(
    val grade: Float,
    val description: String?,
    val clientId: String,
    val supplierId: String,
)
