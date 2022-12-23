package dev.december.jeterbackend.shared.features.admin.domain.models

import dev.december.jeterbackend.shared.features.authorities.domain.models.AuthorityCode

enum class AdminAuthorityCode {
    SUPER_ADMIN,
    ADMIN,
    SUPPORT;

    fun convertToAuthorityCode(): AuthorityCode {
        return AuthorityCode.values()[this.ordinal]
    }
}