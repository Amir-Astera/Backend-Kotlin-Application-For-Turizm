package dev.december.jeterbackend.scheduler.core.config.security

import dev.december.jeterbackend.scheduler.core.config.properties.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.reactive.config.WebFluxConfigurer


@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val securityProperties: SecurityProperties,
): WebFluxConfigurer {

    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
    ): SecurityWebFilterChain {

        http.httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .logout().disable()

        http.authorizeExchange().pathMatchers(HttpMethod.GET, *securityProperties.allowedGetPublicApis.toTypedArray())
            .permitAll()
            .and()
            .authorizeExchange()
            .anyExchange()
            .denyAll()


        return http.build()
    }
}