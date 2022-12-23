package dev.december.jeterbackend.client.features.chats.presentation.dto

data class CreateChatData(
    val id: String,
    val name: String,
    val message: String,
)
