package dev.december.jeterbackend.shared.core.domain.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize

sealed class UserIdentity {
    @JsonDeserialize
    class Phone(val phone: String) : UserIdentity()
    @JsonDeserialize
    class Email(val email: String) : UserIdentity()
    @JsonDeserialize
    class Both(val phone: String, val email: String) : UserIdentity()
}