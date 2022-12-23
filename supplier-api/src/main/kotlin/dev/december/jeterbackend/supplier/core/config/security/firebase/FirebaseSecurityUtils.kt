package dev.december.jeterbackend.supplier.core.config.security.firebase

import dev.december.jeterbackend.supplier.core.config.security.SessionUser
import org.apache.logging.log4j.util.Strings
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.security.Principal

object FirebaseSecurityUtils {

  fun getAuthentication(token: String): Authentication {
    if (!StringUtils.hasText(token)) {
      throw BadCredentialsException("Invalid token")
    }

    return PreAuthenticatedAuthenticationToken(null, token)
  }

  fun getTokenFromRequest(serverWebExchange: ServerWebExchange): String {
    val token = serverWebExchange.request
      .headers
      .getFirst(HttpHeaders.AUTHORIZATION) ?: ""
    return token.ifBlank { Strings.EMPTY }
  }

  fun getUserFromRequest(serverWebExchange: ServerWebExchange): Mono<SessionUser> {
    return serverWebExchange.getPrincipal<Principal>()
      .cast(PreAuthenticatedAuthenticationToken::class.java)
      .map { obj -> obj.principal }
      .cast(SessionUser::class.java)
  }
}