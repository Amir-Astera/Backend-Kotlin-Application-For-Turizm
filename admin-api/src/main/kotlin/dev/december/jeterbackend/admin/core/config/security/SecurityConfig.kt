package dev.december.jeterbackend.admin.core.config.security

import com.google.firebase.auth.FirebaseAuth
import dev.december.jeterbackend.admin.core.config.security.firebase.FirebaseHeadersExchangeMatcher
import dev.december.jeterbackend.admin.core.config.security.firebase.FirebaseTokenAuthenticationManager
import dev.december.jeterbackend.admin.features.appointments.authorization.domain.usecaces.SaveSessionUserUseCase
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(
    private val securityProperties: SecurityProperties
) : WebFluxConfigurer {

    override fun addCorsMappings(corsRegistry: CorsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins(*securityProperties.allowedOrigins.toTypedArray())
            .allowedHeaders(*securityProperties.allowedHeaders.toTypedArray())
            .allowedMethods(*securityProperties.allowedMethods.toTypedArray())
            .exposedHeaders(*securityProperties.exposedHeaders.toTypedArray())
            .maxAge(3600)
    }



    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        entryPoint: UnauthorizedAuthenticationEntryPoint,
        authWebFilter: AuthenticationWebFilter
    ): SecurityWebFilterChain {

        http.httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .logout().disable()

        http.exceptionHandling()
            .authenticationEntryPoint(entryPoint)
            .and()
            .authorizeExchange()
            .pathMatchers(HttpMethod.OPTIONS)
            .permitAll()
            .and()
            .authorizeExchange()
            .pathMatchers(*securityProperties.allowedPublicApis.toTypedArray()).permitAll()
            .and()
            .authorizeExchange().pathMatchers(HttpMethod.GET, *securityProperties.allowedGetPublicApis.toTypedArray())
            .permitAll()
            .and()
            .authorizeExchange()
            .matchers(EndpointRequest.toAnyEndpoint())
            .authenticated()
            .and()
            .addFilterAt(authWebFilter, SecurityWebFiltersOrder.AUTHORIZATION)
            .authorizeExchange()
            .anyExchange()
            .authenticated()

        return http.build()
    }

    @Bean
    fun authWebFilter(authenticationManager: ReactiveAuthenticationManager): AuthenticationWebFilter {
        val authenticationWebFilter = AuthenticationWebFilter(authenticationManager)
        authenticationWebFilter.setServerAuthenticationConverter(TokenAuthenticationConverter())
        authenticationWebFilter.setRequiresAuthenticationMatcher(FirebaseHeadersExchangeMatcher())
        authenticationWebFilter.setSecurityContextRepository(WebSessionServerSecurityContextRepository())
        return authenticationWebFilter
    }

    @Bean
    fun authenticationManager(
        auth: FirebaseAuth,
        saveSessionUserUseCase: SaveSessionUserUseCase
    ): ReactiveAuthenticationManager {
        return FirebaseTokenAuthenticationManager(auth, saveSessionUserUseCase)
    }
}