package dev.december.jeterbackend.stream.signaler.domain.services

import dev.december.jeterbackend.stream.signaler.data.model.ResponseMessageModel
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data

interface MessageService {
    fun save(chatId: String, userId: String, message: String, platformRole: PlatformRole): Data<ResponseMessageModel>
}