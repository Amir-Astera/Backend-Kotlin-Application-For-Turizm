package dev.december.jeterbackend.shared.features.authorities.data.entities

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "authority")
data class AuthorityEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "code", nullable = false, unique = true)
    val code: String,
    @Column(name = "name", nullable = false, unique = true)
    val name: String,
    @Column(name = "description")
    val description: String? = null,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
