package dev.december.jeterbackend.shared.features.articles.data.entities

import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.stories.data.entities.StoriesEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "article")
data class ArticleEntity(
    @Id
    @Column(name = "id", nullable = false, length = 50)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "title", nullable = false, unique = true)
    val title: String,
    @Column(name = "priority", nullable = false)
    val priority: Int,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "articleFiles")
    val files: Set<FileEntity> = emptySet(),
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "article_id")
    val stories: Set<StoriesEntity> = emptySet(),
    @Column(name = "description", columnDefinition= "TEXT", nullable = false)
    val description: String = "",
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "profession_id")
//    val profession: ProfessionEntity? = null,
    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updatedAt", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)