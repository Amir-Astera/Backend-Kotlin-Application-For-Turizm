package dev.december.jeterbackend.shared.features.admin.data.entities.extensions

import dev.december.jeterbackend.shared.features.authorities.domain.models.AuthorityCode
import java.util.*
import javax.persistence.*


@Entity(name = "admin_authority")
data class  AdminAuthorityEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false)
    val authority: AuthorityCode,
)