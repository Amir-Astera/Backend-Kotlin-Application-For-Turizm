package dev.december.jeterbackend.shared.features.authorities.domain.models

data class Authority(
    val id: String,
    val code: String,
    val name: String,
    val description: String?,
)
