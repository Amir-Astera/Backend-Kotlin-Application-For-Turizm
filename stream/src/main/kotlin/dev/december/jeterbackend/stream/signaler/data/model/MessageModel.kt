package dev.december.jeterbackend.stream.signaler.data.model

import dev.december.jeterbackend.shared.features.files.domain.models.File

data class MessageModel(
    val chatId: String,
    val content: String,
    val files: List<File>?,
)