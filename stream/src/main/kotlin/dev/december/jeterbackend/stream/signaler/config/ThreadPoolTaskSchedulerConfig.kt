package dev.december.jeterbackend.stream.signaler.config

import dev.december.jeterbackend.stream.signaler.config.properties.TaskProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class ThreadPoolTaskSchedulerConfig(
    val taskProperties: TaskProperties,
) {
    @Bean
    fun threadPoolTaskScheduler(): ThreadPoolTaskScheduler {
        val threadPoolTaskScheduler = ThreadPoolTaskScheduler()
        threadPoolTaskScheduler.poolSize = taskProperties.poolSize
        threadPoolTaskScheduler.setThreadNamePrefix(taskProperties.threadNamePrefix)
        return threadPoolTaskScheduler
    }
}