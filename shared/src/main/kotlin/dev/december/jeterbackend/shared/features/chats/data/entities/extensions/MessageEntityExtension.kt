package dev.december.jeterbackend.shared.features.chats.data.entities.extensions

import dev.december.jeterbackend.shared.features.chats.data.entities.MessageEntity
import dev.december.jeterbackend.shared.features.chats.domain.models.Message
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier

fun MessageEntity.messages(): Message {
    return convert(
        mapOf(
            "chatId" to chat.id,
            "client" to this.client?.client(),
            "supplier" to this.supplier?.supplier(),
            "files" to this.files.map<FileEntity, File> { it.convert() },
        )
    )
}