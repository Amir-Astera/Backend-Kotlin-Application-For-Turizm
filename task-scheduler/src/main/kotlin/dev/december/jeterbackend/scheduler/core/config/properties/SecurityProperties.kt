package dev.december.jeterbackend.scheduler.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("security")
data class SecurityProperties(
    val allowedGetPublicApis: List<String>,
)