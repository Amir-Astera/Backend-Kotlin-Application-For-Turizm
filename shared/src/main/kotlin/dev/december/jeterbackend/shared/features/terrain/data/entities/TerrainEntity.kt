package dev.december.jeterbackend.shared.features.terrain.data.entities

import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import org.hibernate.Hibernate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity(name = "Terrain")
data class TerrainEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "title", nullable = false, unique = true)
    val title: String,
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "profession_id")
//    val specialities: Set<SpecialityEntity> = emptySet(),
    @Column(name = "description")
    val description: String = "",
    @Column(name = "priority", nullable = false)
    val priority: Int,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "fileId", referencedColumnName = "id")
    val file: FileEntity?,
    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updatedAt", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as TerrainEntity

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title , description = $description , priority = $priority , file = $file , createdAt = $createdAt , updatedAt = $updatedAt )"
    }
}