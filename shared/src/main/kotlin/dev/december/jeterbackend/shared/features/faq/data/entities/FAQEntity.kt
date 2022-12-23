package dev.december.jeterbackend.shared.features.faq.data.entities

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity(name = "faq")
data class FAQEntity(
    @Id
    @Column(name = "id", nullable = false, length = 50)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "title", nullable = false, unique = true)
    val title: String,
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    val description: String = "",
    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updatedAt", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @Enumerated(EnumType.STRING)
    @Column(name="authority", nullable = false)
    val authority: PlatformRole
)