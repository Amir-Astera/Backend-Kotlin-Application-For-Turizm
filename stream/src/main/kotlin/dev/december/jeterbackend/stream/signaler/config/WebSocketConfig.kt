package dev.december.jeterbackend.stream.signaler.config

import dev.december.jeterbackend.stream.signaler.presentation.handlers.SocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.web.socket.config.annotation.*
import java.security.Principal

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val socketHandler: SocketHandler,
    private val firebaseTokenAuthenticationManager: FirebaseTokenAuthenticationManager
) : WebSocketConfigurer, WebSocketMessageBrokerConfigurer {

  override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
    registry.addHandler(socketHandler, "/stream/session")
        .setAllowedOrigins("*")
  }

  override fun configureMessageBroker(registry: MessageBrokerRegistry) {
      registry.enableSimpleBroker("/topic")
      registry.setApplicationDestinationPrefixes("/stream/app")
      registry.setUserDestinationPrefix("/stream/user")
//      registry.setPreservePublishOrder(true)
  }

  override fun registerStompEndpoints(registry: StompEndpointRegistry) {
      registry.addEndpoint("/stream/chat")
          .setAllowedOriginPatterns("*")

      registry.addEndpoint("/stream/chat")
          .setAllowedOriginPatterns("*")
            //            .setHandshakeHandler(UserHandshakeHandler())
          .withSockJS()
  }


    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(object : ChannelInterceptor {
            override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
                val accessor = MessageHeaderAccessor.getAccessor(
                    message,
                    StompHeaderAccessor::class.java
                )
                if (StompCommand.CONNECT == accessor!!.command) {
                    val accessToken = accessor.getFirstNativeHeader("AUTHORIZATION")
                    val encoded = accessToken?.split(':')?.lastOrNull() ?: ""
                    val encodedToken = encoded.split(' ').lastOrNull()
                    val user = firebaseTokenAuthenticationManager.authenticateToken(encodedToken).block() as SessionUser
                    accessor.user = PrincipalUser(user.id)
                }
                return message
            }
        })
    }
}

class PrincipalUser(val id: String) : Principal {
    override fun getName(): String {

        return id
    }
}


