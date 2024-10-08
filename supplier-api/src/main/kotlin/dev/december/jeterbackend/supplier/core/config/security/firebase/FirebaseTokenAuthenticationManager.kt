package dev.december.jeterbackend.supplier.core.config.security.firebase

import com.google.firebase.auth.FirebaseAuth
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.authorization.domain.usecases.SaveSessionUserUseCase
import dev.december.jeterbackend.supplier.features.authorization.domain.usecases.SessionParams
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono

class FirebaseTokenAuthenticationManager(
    private val auth: FirebaseAuth,
    private val saveSessionUserUseCase: SaveSessionUserUseCase
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        if (authentication.isAuthenticated) {
            return Mono.just(authentication)
        }

        return Mono.just(authentication)
            .switchIfEmpty(Mono.defer(::raiseBadCredentials))
            .cast(PreAuthenticatedAuthenticationToken::class.java)
            .flatMap { token -> authenticateToken(token) }
            .publishOn(Schedulers.parallel())
            .onErrorResume { e -> raiseBadCredentials(e) }
            .switchIfEmpty(Mono.defer(::raiseBadCredentials))
            .map { u -> PreAuthenticatedAuthenticationToken(u, authentication.credentials, u.authorities) }
    }

    private fun <T> raiseBadCredentials(): Mono<T>? {
        return Mono.error(BadCredentialsException("Invalid Credentials"))
    }

    private fun <T> raiseBadCredentials(e: Throwable): Mono<T>? {
        return Mono.error(BadCredentialsException("Invalid Credentials", e))
    }

    private fun authenticateToken(authenticationToken: PreAuthenticatedAuthenticationToken): Mono<UserDetails> {
        val token = authenticationToken.credentials as? String
        val authentication = SecurityContextHolder.getContext().authentication

        if (token != null && authentication == null) {
            return auth
                .verifyIdTokenAsync(token, false)
                .toMono()
                .map { it.get() }
                .flatMap {
                    mono { saveSessionUserUseCase(SessionParams(it)) }.map {
                        when (it) {
                            is Data.Success -> it.data
                            is Data.Error -> throw BadCredentialsException("Invalid Credentials")
                        }
                    }
                }
        }
        return Mono.empty()
    }
}
