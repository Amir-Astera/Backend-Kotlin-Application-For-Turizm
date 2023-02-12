package dev.december.jeterbackend.stream.signaler.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.Message
import dev.december.jeterbackend.shared.features.chats.domain.models.SupportMessage
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.stream.core.domain.model.PlatformRole

interface MessageService {
    fun save(
        chatId: String,
        userId: String,
        content: String,
        files: List<File>?,
        platformRole: PlatformRole,
    ): Data<Message>

    fun saveSupportMessage(
        chatId: String,
        userId: String,
        content: String,
        files: List<File>?,
        platformRole: PlatformRole
    ): Data<SupportMessage>

}