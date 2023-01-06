package dev.december.jeterbackend.stream.signaler.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties("security.firebase-props.google-credentials")
data class FirebaseGoogleCredentialsProperties(
    val supplier: String,
    val client: String,
)
