package dev.december.jeterbackend.admin.core.config.security

import dev.december.jeterbackend.admin.core.config.security.firebase.FirebaseProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("security")
data class SecurityProperties(
    val firebaseProps: FirebaseProperties,
    val allowCredentials: Boolean,
    val allowedOrigins: List<String>,
    val allowedHeaders: List<String>,
    val exposedHeaders: List<String>,
    val allowedMethods: List<String>,
    val allowedGetPublicApis: List<String>,
    val allowedPublicApis: List<String>,
)