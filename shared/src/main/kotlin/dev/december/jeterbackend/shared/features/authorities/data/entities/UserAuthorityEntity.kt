package dev.december.jeterbackend.shared.features.authorities.data.entities

import dev.december.jeterbackend.shared.core.domain.model.UserActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.UserEnableStatus
import dev.december.jeterbackend.shared.features.authorities.domain.models.AuthorityCode
import java.util.*
import javax.persistence.*

@Entity(name = "user_authority")
data class  UserAuthorityEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false)
    val authority: AuthorityCode,
    @Enumerated(EnumType.STRING)
    @Column(name = "enable_status", nullable = false)
    val enableStatus: UserEnableStatus,
    @Enumerated(EnumType.STRING)
    @Column(name = "activity_status", nullable = false)
    val activityStatus: UserActivityStatus,
)
