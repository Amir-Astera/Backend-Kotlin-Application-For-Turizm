package dev.december.jeterbackend.supplier

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = ["dev.december.jeterbackend"])
@EnableJpaRepositories("dev.december.jeterbackend")
@EntityScan("dev.december.jeterbackend")
@ConfigurationPropertiesScan
class JeterSupplierApplication

fun main(args: Array<String>) {
	runApplication<JeterSupplierApplication>(*args)
}
