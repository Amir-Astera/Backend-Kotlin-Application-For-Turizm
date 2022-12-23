package dev.december.jeterbackend.stream.signaler.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.io.IOException

@Configuration
class FirebaseConfig {

    @Primary
    @Bean
    @Throws(IOException::class)
    fun init() {
        if (FirebaseApp.getApps().isEmpty()) {
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build()
            FirebaseApp.initializeApp(options)
        }
    }

    @Bean
    fun firebaseAuth(): FirebaseAuth {
        init()
        return FirebaseAuth.getInstance()
    }

    @Bean
    fun firebaseMessaging(): FirebaseMessaging {
        init()
        return FirebaseMessaging.getInstance()
    }
}