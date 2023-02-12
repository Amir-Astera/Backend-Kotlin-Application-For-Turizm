package dev.december.jeterbackend.shared.features.chats.data.entities.extensions

import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.admin.data.entities.extensions.admin
import dev.december.jeterbackend.shared.features.chats.data.entities.SupportMessageEntity
import dev.december.jeterbackend.shared.features.chats.domain.models.SupportMessage
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier

fun SupportMessageEntity.supportMessages(): SupportMessage {
    return convert(
        mapOf(
            "chatId" to chat.id,
            "client" to this.client?.client(),
            "supplier" to this.supplier?.supplier(),
            "admin" to this.admin?.admin(),
            "files" to this.files.map<FileEntity, File> { it.convert() },
        )
    )
}