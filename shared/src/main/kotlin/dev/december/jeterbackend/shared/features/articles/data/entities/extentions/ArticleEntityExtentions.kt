package dev.december.jeterbackend.shared.features.articles.data.entities.extentions

import dev.december.jeterbackend.shared.features.articles.data.entities.ArticleEntity
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.articles.domain.models.Article
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.stories.data.entities.extentions.stories

fun ArticleEntity.article(): Article {
    return convert(
        mapOf(
            "files" to this.files.map<FileEntity, File> { it.convert() },
            "stories" to this.stories.map { it.stories() },
        )
    )
}