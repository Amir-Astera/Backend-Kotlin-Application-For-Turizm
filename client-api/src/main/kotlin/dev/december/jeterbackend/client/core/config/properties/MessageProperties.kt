package dev.december.jeterbackend.client.core.config.properties

import dev.december.jeterbackend.client.core.config.properties.NotificationsProperties
import dev.december.jeterbackend.client.core.config.properties.YamlPropertySourceFactory
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(value = ["classpath:messages.yml"], factory = YamlPropertySourceFactory::class)
data class MessageProperties (
    val notifications: NotificationsProperties
)