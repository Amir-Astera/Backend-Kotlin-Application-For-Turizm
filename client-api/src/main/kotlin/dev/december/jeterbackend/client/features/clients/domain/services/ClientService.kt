package dev.december.jeterbackend.client.features.clients.domain.services

import dev.december.jeterbackend.client.features.clients.presentation.dto.UpdateClientData
import dev.december.jeterbackend.shared.core.domain.model.UserGender
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.files.domain.models.File
import org.springframework.data.domain.Page
import java.time.LocalDate

interface ClientService {
    suspend fun create(
        email: String,
        phone: String,
        password: String,
        fullName: String,
        birthDate: LocalDate?,
        gender: UserGender?,
        avatar: File?,
        registrationToken: String,
        successOnExists: ((ClientEntity) -> Boolean)? = null,
    ): Data<String>

    suspend fun addUserClaims(userId: String, claims: Map<String, Any>): Data<String>

    suspend fun get(
        userId: String
    ): Data<Unit>//Client

    suspend fun update(
        userId: String,
        updateClientData: UpdateClientData?
    ): Data<String>

    suspend fun deleteByUserId(
        userId: String,
        signInProvider: String?
    ): Data<String>

    suspend fun addSupplierToFavorites(
        userId: String,
        supplier: String,
    ): Data<Unit>

    suspend fun getFavoriteSuppliers(
        userId: String,
        page: Int,
        size: Int,
    ): Data<Unit>//Page<Supplier>

    suspend fun deleteSupplierFromFavorites(
        userId: String,
        supplier: String,
    ): Data<Unit>

    suspend fun getByPhone(phone: String): Data<Boolean>

    suspend fun updateRegistrationToken(userId: String, registrationToken: String): Data<Unit>

    suspend fun restore(id: String, signInProvider: String?): Data<Unit>
}
