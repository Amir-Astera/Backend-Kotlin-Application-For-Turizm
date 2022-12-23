package dev.december.jeterbackend.admin.features.clients.data.services

import dev.december.jeterbackend.admin.features.clients.domain.errors.ClientDeleteFailure
import dev.december.jeterbackend.admin.features.clients.domain.errors.ClientListDeleteFailure
import dev.december.jeterbackend.admin.features.clients.domain.errors.ClientListGetFailure
import dev.december.jeterbackend.admin.features.clients.domain.errors.ClientNotFoundFailure
import dev.december.jeterbackend.admin.features.clients.domain.services.ClientService
import dev.december.jeterbackend.admin.features.clients.presentation.dto.UpdateClientData
import dev.december.jeterbackend.admin.features.suppliers.domain.errors.SupplierGetFailure
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.domain.model.UserGender
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.authorities.data.entities.UserAuthorityRepository
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.clients.data.repositories.specifications.ClientSpecification
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.clients.domain.models.ClientSortField
import dev.december.jeterbackend.shared.features.clients.domain.models.ClientUser
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
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
    private val userAuthorityRepository: UserAuthorityRepository,
) : ClientService {

    override suspend fun getList(
        sortField: ClientSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        searchField: String?,
        activityStatuses: Set<AccountActivityStatus>?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
        enableStatus: AccountEnableStatus?
    ): Data<Page<Client>> {
        return try {
            withContext(dispatcher) {
                val sortParams = sortField.getSortFields(sortDirection)
                val pageable = PageRequest.of(page, size, sortParams)

                val specification =
                    Specification.where(ClientSpecification.hasEnableStatus(enableStatus))
                        .and(ClientSpecification.containsFullName(searchField))
                        .and(ClientSpecification.isGreaterThanCreatedAt(createdFrom))
                        .and(ClientSpecification.isLessThanCreatedAt(createdTo))
                        .and(ClientSpecification.isInActivityStatus(activityStatuses))

                val clientEntities = clientRepository.findAll(specification, pageable)

                val clients = clientEntities.map { it.client() }
                Data.Success(clients)
            }
        } catch (e: Exception) {
            return Data.Error(ClientListGetFailure())
        }
    }

//    override suspend fun create(
//        userId: String,
//        login: String,
//        fullName: String,
//        birthDate: LocalDate?,
//        gender: UserGender?,
//        avatarId: String?,
//        registrationToken: String?
//    ): Data<String> {
//        return try {
//            withContext(dispatcher){
//
//                val oldUser = userRepository.findByIdOrNull(userId)
//                if (oldUser?.client != null) {
//                    return@withContext Data.Error(ClientAlreadyExistFailure())
//                }
//
//                val avatarFile = if (avatarId != null) {
//                    fileRepository.findByIdOrNull(avatarId) ?: return@withContext Data.Error(
//                        FileNotFoundFailure()
//                    )
//                } else null
//
//                val client = ClientEntity(
//                    avatar = avatarFile,
//                    fullName = fullName,
//                    birthDate = birthDate,
//                    userGender = gender ?: UserGender.UNKNOWN,
//                    registrationToken = registrationToken
//                )
//
//                clientRepository.save(client)
//
//                val userAuthorityEntity = UserAuthorityEntity(
//                        authority = AuthorityCode.CLIENT,
//                        enableStatus = UserEnableStatus.ENABLED,
//                        activityStatus = UserActivityStatus.ACTIVE,
//                    )
//
//                val user = userRepository.findByIdOrNull(userId)
//                if ( user == null) {
//                    val isPhone = dev.bytepride.truprobackend.core.utils.check(login)
//                    val newUser = UserEntity(
//                        id = userId,
//                        phone = if (isPhone) login else null,
//                        email = if (!isPhone) login else null,
//                        client = client,
//                        userAuthorities = mutableSetOf(userAuthorityEntity)
//                    )
//                    userRepository.save(newUser)
//                }else{
//                    val userAuthority = user.userAuthorities
//                    val userWithClient = user.copy(
//                        client = client,
//                        userAuthorities = userAuthority.plus(userAuthorityEntity)
//                    )
//                    userRepository.save(userWithClient)
//                }
//
//                Data.Success(client.id)
//            }
//        } catch (e: Exception){
//            Data.Error(CreateGeneralInfoSupplierFailure())
//        }
//    }

    override suspend fun get(userId: String): Data<ClientUser> {
        return try {
            withContext(dispatcher) {

//                val clientEntity = userRepository.findByIdOrNull(userId)?.client ?: return@withContext Data.Error(
//                    ClientNotFoundFailure()
//                )

//                val clientUser = ClientUser(
//                    id = clientEntity.id,
//                    sessionCount = clientEntity.sessionCount,
//                    expenses = clientEntity.expenses,
//                    avatar = clientEntity.avatar,
//                    fullName = clientEntity?.fullName,
//                    birthDate = clientEntity?.birthDate,
//                    gender = clientEntity?.userGender
//                )

                val clientUser = ClientUser("", 2, 2.2F, null, null, null, null)


                Data.Success(clientUser)
            }
        } catch (e: Exception) {
            println(e.message)
            Data.Error(SupplierGetFailure())
        }
    }

    override suspend fun update(
        userId: String,
        updateClientData: UpdateClientData?
    ): Data<String> {
        return try {
            withContext(dispatcher) {

//                val oldClientEntity =
//                    userRepository.findByIdOrNull(userId)?.client ?: return@withContext Data.Error(ClientNotFoundFailure())
//
//                val avatarFile = if (updateClientData?.avatarId != null) {
//                    fileRepository.findByIdOrNull(updateClientData.avatarId) ?: return@withContext Data.Error(
//                        FileNotFoundFailure()
//                    )
//                } else null
//
//                val newClientEntity = oldClientEntity.copy(
//                    id = oldClientEntity.id,
//                    sessionCount = updateClientData?.sessionCount ?: oldClientEntity.sessionCount,
//                    expenses = updateClientData?.expenses ?: oldClientEntity.expenses,
//                    fullName = updateClientData?.fullName ?: oldClientEntity.fullName,
//                    birthDate = updateClientData?.birthDate ?: oldClientEntity.birthDate,
//                    userGender = updateClientData?.gender ?: oldClientEntity.userGender,
//                    avatar = avatarFile ?: oldClientEntity.avatar,
//                )
//
//                clientRepository.save(newClientEntity)
//
//                val oldUserEntity = oldClientEntity.user ?: return@withContext Data.Error(UserNotFoundFailure())
//                val newUserEntity = oldUserEntity.copy(
//                    id = oldUserEntity.id,
//                    phone = updateClientData?.phone ?: oldUserEntity.phone
//                )
//
//                userRepository.save(newUserEntity)

                Data.Success("client updated")
            }
        } catch (e: Exception) {
            println(e.message)
            Data.Error(SupplierGetFailure())
        }
    }

    override suspend fun deleteByUserId(userId: String): Data<String> {
        return try {
            withContext(dispatcher) {
//
//                val userEntity = userRepository.findByIdOrNull(userId) ?: return@withContext  Data.Error(
//                    UserNotFoundFailure()
//                )
//                clientRepository.save(
//                    userEntity.client?.copy(
//                        enableStatus = AccountEnableStatus.DISABLED
//                    ) ?: return@withContext Data.Error(ClientNotFoundFailure())
//                )
                Data.Success(" successfully deleted!!")//userEntity.client?.fullName +
            }
        } catch (e: Exception) {
            println(e.message)
            Data.Error(ClientListDeleteFailure())
        }
    }

    override suspend fun delete(id: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val clientEntity = clientRepository.findByIdOrNull(id) ?:
                    return@withContext Data.Error(ClientNotFoundFailure())
                clientRepository.save(
                    clientEntity.copy(enableStatus = AccountEnableStatus.DISABLED)
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(ClientDeleteFailure())
        }
    }

    override suspend fun deleteList(ids: Set<String>): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val clientEntities = clientRepository.findAllById(ids)
                if (clientEntities.count() != ids.size) {
                    return@withContext Data.Error(ClientNotFoundFailure())
                }
                clientRepository.saveAll(
                    clientEntities.map {
                        it.copy(enableStatus = AccountEnableStatus.DISABLED)
                    }
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(ClientDeleteFailure())
        }
    }

    override suspend fun enable(id: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val clientEntity = clientRepository.findByIdOrNull(id) ?: return@withContext Data.Error(
                    ClientNotFoundFailure()
                )
                clientRepository.save(
                    clientEntity.copy(
                        enableStatus = AccountEnableStatus.ENABLED
                    )
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(ClientNotFoundFailure())
        }
    }


}