package dev.december.jeterbackend.shared.features.authorities.domain.models

import org.springframework.security.core.GrantedAuthority

enum class AuthorityCode : GrantedAuthority {
    SUPER_ADMIN,
    ADMIN,
    SUPPORT,
    SUPPLIER,
    CLIENT;

    override fun getAuthority(): String {
        return this.name
    }
}