package dev.december.jeterbackend.shared.features.stories.data.entities.extentions

import dev.december.jeterbackend.shared.features.stories.data.entities.StoriesEntity
import dev.december.jeterbackend.shared.features.stories.domain.models.Stories
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier


fun StoriesEntity.stories(): Stories {
    return convert(
        mapOf(
            "files" to this.files.map<FileEntity, File> { it.convert() },
            "suppliers" to (this.suppliers?.map { it.supplier() }?.toSet())
        )
    )
}