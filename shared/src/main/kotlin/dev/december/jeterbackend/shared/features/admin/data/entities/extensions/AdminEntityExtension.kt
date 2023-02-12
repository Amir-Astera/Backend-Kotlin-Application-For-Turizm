package dev.december.jeterbackend.shared.features.admin.data.entities.extensions

import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.admin.data.entities.AdminEntity
import dev.december.jeterbackend.shared.features.admin.domain.models.Admin
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.domain.models.File

fun AdminEntity.admin(): Admin {
    return convert(
        mapOf(
            "avatar" to this.file?.convert<FileEntity, File>(),
            "authorities" to this.adminAuthorities
        )
    )
}