package dev.december.jeterbackend.shared.features.files.domain.models

import com.fasterxml.jackson.annotation.JsonProperty

enum class FileDirectory {
    @JsonProperty("article")
    ARTICLE,
    @JsonProperty("tutorial")
    TUTORIAL,
    @JsonProperty("user")
    USER,
    @JsonProperty("client")
    CLIENT,
    @JsonProperty("supplier")
    SUPPLIER,
    @JsonProperty("supplier_passport")
    SUPPLIER_PASSPORT,
    @JsonProperty("supplier_education")
    SUPPLIER_EDUCATION,
    @JsonProperty("supplier_certificate")
    SUPPLIER_CERTIFICATE,
    @JsonProperty("supplier_experience")
    SUPPLIER_EXPERIENCE,
    @JsonProperty("profession")
    PROFESSION,
    @JsonProperty("chat_media")
    CHAT_MEDIA,
}