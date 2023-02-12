package dev.december.jeterbackend.stream.signaler.config

import com.google.firebase.auth.FirebaseAuth
import dev.december.jeterbackend.shared.core.results.Data
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class FirebaseTokenAuthenticationManager(
    private val auth: FirebaseAuth,
    private val saveSessionUserUseCase: SaveSessionUserUseCase,
)  {
    fun authenticateToken(token: String?): Mono<UserDetails> {
        if (token != null) {
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
