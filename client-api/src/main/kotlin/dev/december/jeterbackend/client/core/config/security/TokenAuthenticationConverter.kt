package dev.december.jeterbackend.client.core.config.security

import dev.december.jeterbackend.client.core.config.security.firebase.FirebaseSecurityUtils
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*
import java.util.function.Function
import java.util.function.Predicate

class TokenAuthenticationConverter : ServerAuthenticationConverter {

    companion object {
        private const val BEARER = "Bearer "
        private val properBearer = Predicate { authValue: String -> authValue.length > BEARER.length && authValue.startsWith(BEARER) }
        private val isolateBearerValue = Function { authValue: String -> authValue.substring(BEARER.length) }
    }

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        return Mono.justOrEmpty(exchange)
            .map(FirebaseSecurityUtils::getTokenFromRequest)
            .filter { obj -> Objects.nonNull(obj) }
            .filter(properBearer)
            .map(isolateBearerValue)
            .filter { token -> StringUtils.hasText(token) }
            .map(FirebaseSecurityUtils::getAuthentication)
    }
}