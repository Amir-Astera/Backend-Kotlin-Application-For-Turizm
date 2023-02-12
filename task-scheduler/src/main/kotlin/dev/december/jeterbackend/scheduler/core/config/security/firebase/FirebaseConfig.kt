package dev.december.jeterbackend.scheduler.core.config.security.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import dev.december.jeterbackend.scheduler.core.config.properties.FirebaseGoogleCredentialsProperties
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import org.springframework.stereotype.Component
import java.io.FileInputStream
import java.io.IOException

@Component
class FirebaseConfig(
    private val googleCredentialsProperties: FirebaseGoogleCredentialsProperties,
) {

    init {
        initFor(FIREBASE_APP_SUPPLIER, googleCredentialsProperties.supplier)
        initFor(FIREBASE_APP_CLIENT, googleCredentialsProperties.client)
    }

    companion object {
        const val FIREBASE_APP_SUPPLIER = "SUPPLIER"
        const val FIREBASE_APP_CLIENT = "CLIENT"
    }

    @Throws(IOException::class)
    private fun initFor(name: String, credentialsPath: String) {
        val stream = FileInputStream(credentialsPath)
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(stream))
            .build()
        FirebaseApp.initializeApp(options, name)
    }

    private fun getNameFromRole(role: PlatformRole): String{
        return when(role) {
            PlatformRole.SUPPLIER -> FIREBASE_APP_SUPPLIER
            PlatformRole.CLIENT -> FIREBASE_APP_CLIENT
        }
    }

    fun firebaseAuth(role: PlatformRole): FirebaseAuth {
        return FirebaseAuth.getInstance(FirebaseApp.getInstance(getNameFromRole(role)))
    }

    fun firebaseMessaging(role: PlatformRole): FirebaseMessaging {
        return FirebaseMessaging.getInstance(FirebaseApp.getInstance(getNameFromRole(role)))
    }
}