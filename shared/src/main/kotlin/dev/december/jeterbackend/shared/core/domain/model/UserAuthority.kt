package dev.december.jeterbackend.shared.core.domain.model

import dev.december.jeterbackend.shared.features.authorities.domain.models.AuthorityCode

data class UserAuthority(
    val code: AuthorityCode,
    val enableStatus: AccountEnableStatus,
    val activityStatus: AccountActivityStatus,
)
