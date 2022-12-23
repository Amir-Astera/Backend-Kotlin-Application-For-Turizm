package dev.december.jeterbackend.admin.core.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.reactor.asCoroutineDispatcher
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.scheduler.Schedulers
import java.util.concurrent.Executors


@Configuration
class RepositoryDispatcherConfig {

  @Bean
  fun dispatcher(
      @Value("\${spring.datasource.hikari.maximum-pool-size}")
      connectionPoolSize: Int,
  ): CoroutineDispatcher {
    return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize)).asCoroutineDispatcher()
  }
}