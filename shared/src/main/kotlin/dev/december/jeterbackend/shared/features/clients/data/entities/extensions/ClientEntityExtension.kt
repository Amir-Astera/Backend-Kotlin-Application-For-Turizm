package dev.december.jeterbackend.shared.features.clients.data.entities.extensions

import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.domain.models.File

fun ClientEntity.client(): Client {
    return convert(
        mapOf(
            "avatar" to this.avatar?.convert<FileEntity, File>(),
            "file" to this.file?.convert<FileEntity, File>()
        )
    )
}