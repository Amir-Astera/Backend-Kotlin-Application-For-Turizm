package dev.december.jeterbackend.shared.features.chats.data.entities.extensions

import dev.december.jeterbackend.shared.features.chats.data.entities.ChatEntity
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.chats.domain.models.Chat

fun ChatEntity.chat(): Chat {
    return convert(
        mapOf(
            "chatId" to this.id,
            "client" to this.client.client(),
            "supplier" to this.supplier.supplier(),
            "messages" to (this.messages?.map { it.messages() }?.sortedBy { it.createdAt }?.toSet())
        )
    )
}