package dev.december.jeterbackend.stream.signaler.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties(prefix = "task")
data class TaskProperties(
    var poolSize: Int,
    var threadNamePrefix: String,
)
