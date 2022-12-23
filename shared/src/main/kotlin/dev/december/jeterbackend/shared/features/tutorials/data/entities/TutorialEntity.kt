package dev.december.jeterbackend.shared.features.tutorials.data.entities

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "tutorial")
data class TutorialEntity (
   @Id
   @Column(name = "id", nullable = false, length = 50)
   val id : String = UUID.randomUUID().toString(),
   @Column(name = "title", unique = true, nullable = false)
   val title : String,
   @Column(name = "description", columnDefinition= "TEXT", nullable = false)
   val description : String,
   @Column(name = "priority", nullable = false)
   val priority : Int,
   @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
   @JoinColumn(name = "fileId", referencedColumnName = "id")
   val file : FileEntity?,
   @Column(name = "createdAt", nullable = false)
   val createdAt : LocalDateTime = LocalDateTime.now(),
   @Column(name = "updatedAt", nullable = false)
   val updatedAt : LocalDateTime = LocalDateTime.now(),
   @Enumerated(EnumType.STRING)
   @Column(name = "authority", nullable = false)
   val authority: PlatformRole
)