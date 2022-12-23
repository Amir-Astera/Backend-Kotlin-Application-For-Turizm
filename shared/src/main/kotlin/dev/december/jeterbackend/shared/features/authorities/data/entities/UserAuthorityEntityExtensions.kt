package dev.december.jeterbackend.shared.features.authorities.data.entities

import dev.december.jeterbackend.shared.features.authorities.data.entities.UserAuthorityEntity
import dev.december.jeterbackend.shared.core.domain.model.UserAuthority

fun UserAuthorityEntity.authority(): UserAuthority {
    return UserAuthority(this.authority, this.enableStatus, this.activityStatus)
}