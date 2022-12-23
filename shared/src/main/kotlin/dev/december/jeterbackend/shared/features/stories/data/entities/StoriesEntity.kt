package dev.december.jeterbackend.shared.features.stories.data.entities

import dev.december.jeterbackend.shared.features.articles.data.entities.ArticleEntity
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import org.hibernate.Hibernate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "stories")
data class StoriesEntity(
    @Id
    @Column(name = "id", nullable = false, length = 50)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "title")
    val title: String,
    @Column(name = "priority", nullable = false)
    val priority: Int = 0,
    @Column(name = "html_content", columnDefinition= "TEXT", nullable = false)
    val html_content: String = "",
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "storiesFiles")
    val files: Set<FileEntity> = emptySet(),
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="stories_supplier",
        joinColumns = [JoinColumn(name = "stories_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "supplier_id", referencedColumnName = "id")]
    )
    val suppliers: Set<SupplierEntity>? = emptySet(),//Admin
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private val article: ArticleEntity? = null,
    @Column(name = "updatedAt", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as StoriesEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title , priority = $priority , html_content = $html_content , updatedAt = $updatedAt , createdAt = $createdAt )"
    }
}