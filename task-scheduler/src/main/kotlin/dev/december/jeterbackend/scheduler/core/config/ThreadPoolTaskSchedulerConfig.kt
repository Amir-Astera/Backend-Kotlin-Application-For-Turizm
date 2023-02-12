package dev.december.jeterbackend.scheduler.core.config

import dev.december.jeterbackend.scheduler.core.config.properties.TaskProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class ThreadPoolTaskSchedulerConfig(
    val taskProperties: TaskProperties
) {
    @Bean
    fun threadPoolTaskScheduler(): ThreadPoolTaskScheduler {
        val threadPoolTaskScheduler = ThreadPoolTaskScheduler()
        threadPoolTaskScheduler.poolSize = taskProperties.poolSize
        threadPoolTaskScheduler.setThreadNamePrefix(taskProperties.threadNamePrefix)
        return threadPoolTaskScheduler
    }
}