package dev.december.jeterbackend.client.features.clients.data.services

import com.google.firebase.auth.AuthErrorCode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserRecord
import dev.december.jeterbackend.client.features.clients.domain.services.ClientService
import dev.december.jeterbackend.client.features.analytics.domain.services.AnalyticsCounterService
import dev.december.jeterbackend.client.features.clients.domain.errors.*
import dev.december.jeterbackend.client.features.clients.presentation.dto.UpdateClientData
import dev.december.jeterbackend.client.features.files.domain.error.FileNotFoundFailure
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.Gender
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.FavoriteSupplierEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.FavoriteSupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.specifications.FavoriteSupplierSpecification
import dev.december.jeterbackend.shared.features.suppliers.domain.errors.SupplierNotFoundFailure
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import dev.december.jeterbackend.shared.features.user.domain.errors.UserEmailAlreadyExistsFailure
import dev.december.jeterbackend.shared.features.user.domain.errors.UserNotFoundFailure
import dev.december.jeterbackend.shared.features.user.domain.errors.UserPhoneAlreadyExistsFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ClientServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val fileRepository: FileRepository,
    private val clientRepository: ClientRepository,
    private val supplierRepository: SupplierRepository,
    private val favoriteSupplierRepository: FavoriteSupplierRepository,
    private val analyticsCounterService: AnalyticsCounterService,
    private val firebaseAuth: FirebaseAuth
) : ClientService {

    override suspend fun create(
        email: String,
        phone: String,
        password: String,
        fullName: String,
        birthDate: LocalDate?,
        gender: Gender?,
        avatar: File?,
        registrationToken: String?,
        successOnExists: ((ClientEntity) -> Boolean)?
    ): Data<String> {
        return try {
            withContext(dispatcher) {

                 val clientByEmail = clientRepository.findByEmail(email)
                if (clientByEmail != null) {
                    return@withContext Data.Error(UserEmailAlreadyExistsFailure(email = email))
                }

                val clientByPhone = clientRepository.findByPhone(phone)
                if (clientByPhone != null) {
                        return@withContext Data.Error(UserPhoneAlreadyExistsFailure(phone = phone))
                }

                val request = UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPhoneNumber(phone)
                    .setPassword(password)
                val record = try {
                    firebaseAuth.createUser(request)
                } catch (e: FirebaseAuthException) {
                    val updateRequest = when (e.authErrorCode) {
                        AuthErrorCode.EMAIL_ALREADY_EXISTS -> {
                            UserRecord.UpdateRequest(firebaseAuth.getUserByEmail(email).uid)
                                .setPhoneNumber(phone)
                                .setPassword(password)
                        }
                        AuthErrorCode.PHONE_NUMBER_ALREADY_EXISTS -> {
                            UserRecord.UpdateRequest(firebaseAuth.getUserByPhoneNumber(phone).uid)
                                .setEmail(email)
                                .setPassword(password)
                        }
                        else -> throw e
                    }
                    firebaseAuth.updateUser(updateRequest)
                }
                val avatarFile = if (avatar != null) {
                    fileRepository.findByIdOrNull(avatar.id) ?: return@withContext Data.Error(
                        FileNotFoundFailure()
                    )
                } else null

                val client = ClientEntity(
                    id = record.uid,
                    email = email,
                    phone = phone,
                    avatar = avatarFile,
                    fullName = fullName,
                    birthDate = birthDate,
                    userGender = gender ?: Gender.UNKNOWN,
                    registrationToken = registrationToken
                )

                clientRepository.save(client)
                analyticsCounterService.countCreate()
                Data.Success(client.id)
            }
        } catch (e: Exception) {
            Data.Error(ClientCreateFailure())
        }
    }

    override suspend fun addUserClaims(userId: String, claims: Map<String, Any>): Data<String> {
        return try {
            withContext(dispatcher) {
                val user = firebaseAuth.getUser(userId)
                val customClaims = mutableMapOf<String, Any>()
                customClaims.putAll(user.customClaims)
                customClaims.putAll(claims)
                firebaseAuth.setCustomUserClaims(user.uid, customClaims)
                Data.Success(user.uid)
            }
        } catch (e: Exception) {
            Data.Error(ClientNotFoundFailure())
        }
    }

    override suspend fun get(userId: String): Data<Client> {
        return try {
            withContext(dispatcher) {

                val clientEntity = clientRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(ClientNotFoundFailure()
                )

                Data.Success(clientEntity.client())
            }
        } catch (e: Exception) {
            println(e.message)
            Data.Error(ClientNotFoundFailure())
        }
    }

    override suspend fun update(
        userId: String,
        updateClientData: UpdateClientData?
    ): Data<String> {
        return try {
            withContext(dispatcher) {
                val oldClientEntity = clientRepository.findByIdOrNull(userId)
                    ?: return@withContext Data.Error(ClientNotFoundFailure())

                val avatarFile = if (updateClientData?.avatar != null) {
                    fileRepository.findByIdOrNull(updateClientData.avatar.id) ?: return@withContext Data.Error(
                        FileNotFoundFailure()
                    )
                } else null


                val newClientEntity = oldClientEntity.copy(
                    id = oldClientEntity.id,
                    fullName = updateClientData?.fullName ?: oldClientEntity.fullName,
                    birthDate = updateClientData?.birthDate ?: oldClientEntity.birthDate,
                    userGender = updateClientData?.gender ?: oldClientEntity.userGender,
                    avatar = avatarFile ?: oldClientEntity.avatar,
                    updatedAt = LocalDateTime.now()
                )
                clientRepository.save(newClientEntity)
                Data.Success("client updated")
            }
        } catch (e: Exception) {
            println(e.message)
            Data.Error(ClientNotFoundFailure())
        }
    }

    override suspend fun deleteByUserId(userId: String, signInProvider: String?): Data<String> {
        return try {
            withContext(dispatcher) {
                if (signInProvider != "phone") {
                    return@withContext Data.Error(ClientOtpFailure())
                }
                val client = clientRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(ClientNotFoundFailure())
                clientRepository.save(
                    client.copy(
                        enableStatus = AccountEnableStatus.DISABLED
                    )
                )
                Data.Success(client.fullName + " successfully deleted!!")
            }
        } catch (e: Exception) {
            println(e.message)
            Data.Error(ClientListDeleteFailure())
        }
    }

    override suspend fun addSupplierToFavorites(userId: String, supplier: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val clientEntity = clientRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(ClientNotFoundFailure())
                val supplierEntity = supplierRepository.findByIdOrNull(supplier) ?: return@withContext Data.Error(SupplierNotFoundFailure())
                if(favoriteSupplierRepository.existsByClientIdAndSupplierId(clientEntity.id, supplierEntity.id))
                    return@withContext Data.Error(FavoriteSuppliersAlreadyFailure())
                favoriteSupplierRepository.save(
                    FavoriteSupplierEntity (
                        supplier = supplierEntity,
                        client = clientEntity
                    )
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(FavoriteSuppliersAddFailure())
        }
    }

    override suspend fun getFavoriteSuppliers(userId: String, page: Int, size: Int): Data<Page<Supplier>> {
        return try {
            withContext(dispatcher) {
                val clientEntity = clientRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(ClientNotFoundFailure())
                val clientId = clientEntity.id
                val pageable = PageRequest.of(page, size,)
                val specifications =
                    Specification.where(FavoriteSupplierSpecification.clientJoinFilter(clientId))
                .and(FavoriteSupplierSpecification.approvedFilter())
                .and(FavoriteSupplierSpecification.enabledFilter())
                val suppliers = favoriteSupplierRepository.findAll(specifications, pageable).map { favoriteSupplierEntity ->
                    favoriteSupplierEntity.supplier.supplier(isFavorite = true)
                }
                Data.Success(suppliers)
            }
        } catch (e: Exception) {
            Data.Error(FavoriteSuppliersGetFailure())
        }
    }

    override suspend fun deleteSupplierFromFavorites(userId: String, supplier: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val clientEntity = clientRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(ClientNotFoundFailure())
                val supplierEntity = supplierRepository.findByIdOrNull(supplier) ?: return@withContext Data.Error(SupplierNotFoundFailure())
                if(!favoriteSupplierRepository.existsByClientIdAndSupplierId(clientEntity.id, supplierEntity.id))
                    return@withContext Data.Error(FavoriteSuppliersNotFoundFailure())
                clientRepository.save(clientEntity.copy(favoriteSupplier = clientEntity.favoriteSupplier.minus(supplierEntity)))
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(FavoriteSuppliersDeleteFailure())
        }
    }

    override suspend fun getByPhone(phone: String): Data<Boolean> {
        return try {
            withContext(dispatcher) {
                val clientEntity = clientRepository.findByPhone(phone)
                Data.Success(clientEntity != null)
            }
        } catch (e: Exception) {
            Data.Error(ClientNotFoundFailure())//get
        }
    }

    override suspend fun updateRegistrationToken(userId: String, registrationToken: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val clientEntity = clientRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(ClientNotFoundFailure())

                clientRepository.save(
                    clientEntity.copy(
                        registrationToken = registrationToken
                    )
                )

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(ClientRegistrationTokenUpdateFailure())
        }
    }

    override suspend fun restore(id: String, signInProvider: String?): Data<Unit> {
        return try {
            withContext(dispatcher) {
                if (signInProvider != "phone") {
                    return@withContext Data.Error(ClientOtpFailure())
                }
                val client = clientRepository.findByIdOrNull(id) ?: return@withContext Data.Error(ClientNotFoundFailure())
                clientRepository.save(
                    client.copy(
                        enableStatus = AccountEnableStatus.ENABLED
                    )
                )
                Data.Success(Unit)
            }
        } catch(e: Exception) {
            Data.Error(ClientRestoreFailure())
        }
    }
}