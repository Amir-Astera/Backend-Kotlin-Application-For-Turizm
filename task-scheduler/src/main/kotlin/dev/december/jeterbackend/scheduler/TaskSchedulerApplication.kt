package dev.december.jeterbackend.scheduler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["dev.december.jeterbackend"])
@EnableJpaRepositories("dev.december.jeterbackend")
@EntityScan("dev.december.jeterbackend")
@ConfigurationPropertiesScan
class TaskSchedulerApplication

fun main(args: Array<String>) {
    runApplication<TaskSchedulerApplication>(*args)
}
