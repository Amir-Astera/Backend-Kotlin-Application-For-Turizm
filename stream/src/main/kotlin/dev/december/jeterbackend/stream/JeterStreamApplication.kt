package dev.december.jeterbackend.stream

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(exclude=[ SecurityAutoConfiguration::class, ManagementWebSecurityAutoConfiguration::class])
@EnableJpaRepositories("dev.december.jeterbackend")
@EntityScan("dev.december.jeterbackend")
@ConfigurationPropertiesScan
class JeterStreamApplication

fun main(args: Array<String>) {
    runApplication<JeterStreamApplication>(*args)
}
