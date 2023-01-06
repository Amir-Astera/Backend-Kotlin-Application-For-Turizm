package dev.december.jeterbackend.stream.signaler.domain.services

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.chats.domain.models.Message
import dev.december.jeterbackend.shared.features.files.domain.models.File

interface MessageService {
    fun save(
        chatId: String,
        userId: String,
        content: String,
        files: List<File>?,
        platformRole: PlatformRole,
    ): Data<Message>
}