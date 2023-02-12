package dev.december.jeterbackend.supplier.core.config.properties

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(value = ["classpath:messages.yml"], factory = YamlPropertySourceFactory::class)
data class MessageProperties (
    val notifications: NotificationsProperties
)