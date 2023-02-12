package dev.december.jeterbackend.scheduler.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("security.firebase-props.google-credentials")
data class FirebaseGoogleCredentialsProperties(
    val supplier: String,
    val client: String,
)
