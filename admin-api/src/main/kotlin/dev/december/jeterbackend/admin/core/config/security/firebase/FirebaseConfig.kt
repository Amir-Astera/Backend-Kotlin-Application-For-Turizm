package dev.december.jeterbackend.admin.core.config.security.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
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
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .build()

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
    }


    @Bean
    fun firebaseAuth(): FirebaseAuth {
        init()
        return FirebaseAuth.getInstance()
    }
}