package dev.december.jeterbackend.admin.features.admin.data.services

import com.google.firebase.auth.FirebaseAuth
import dev.december.jeterbackend.admin.features.admin.domain.errors.AdminListGetFailure
import dev.december.jeterbackend.admin.features.admin.domain.services.AdminService
import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminByUserIdData
import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminData
import dev.december.jeterbackend.admin.features.admin.domain.errors.AdminNotFoundFailure
import dev.december.jeterbackend.admin.features.files.domain.error.FileNotFoundFailure
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.domain.model.Gender
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.admin.data.entities.AdminEntity
import dev.december.jeterbackend.shared.features.admin.data.entities.extensions.admin
import dev.december.jeterbackend.shared.features.admin.data.repositories.AdminRepository
import dev.december.jeterbackend.shared.features.admin.data.repositories.specifications.AdminSpecification
import dev.december.jeterbackend.shared.features.admin.domain.models.Admin
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminAuthorityCode
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminSortField
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository,
    private val fileRepository: FileRepository,
    private val dispatcher: CoroutineDispatcher,
    private val firebaseAuth: FirebaseAuth
) : AdminService {

    override suspend fun getList(
        sortField: AdminSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        searchField: String?,
        authorityCodes: Set<AdminAuthorityCode>,
        activityStatuses: Set<AccountActivityStatus>?,
        enableStatus: AccountEnableStatus?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Page<Admin>> {
        return try {
            withContext(dispatcher) {
                val sortParams = sortField.getSortFields(sortDirection)

                val pageable = PageRequest.of(page, size, sortParams)

                val specifications =
                    Specification.where(AdminSpecification.hasEnableStatus(enableStatus))
                        .and(AdminSpecification.containsFullName(searchField))
                        .and(AdminSpecification.isGreaterThanCreatedAt(createdFrom))
                        .and(AdminSpecification.isLessThanCreatedAt(createdTo))
                        .and(AdminSpecification.isInActivityStatus(activityStatuses))
                        .and(AdminSpecification.userJoinFilter(authorityCodes))

                val entities = adminRepository.findAll(specifications, pageable)

                val admins = entities.map { it.admin() }
                Data.Success(admins)
            }
        } catch (e: Exception) {
            Data.Error(AdminListGetFailure())
        }
    }

    override suspend fun create(
        email: String,
        phone: String,
        password: String,
        fullName: String,
        birthDate: LocalDate?,
        gender: Gender?,
        avatarId: String?,
        successOnExists: ((AdminEntity) -> Boolean)?,
    ): Data<String> {
        return try {
            if (!StringUtils.hasText(email) && !StringUtils.hasText(phone) || !email.contains("@") || !email.contains(".")) {
                return Data.Error(AdminNotFoundFailure())
            }

            if (!StringUtils.hasText(password) || (StringUtils.hasText(password) && password.length < 8)) {
                return Data.Error(AdminNotFoundFailure())//UserInvalidPasswordFailure
            }

            if (phone.indexOf("+") != 0 || phone.length < 12) {
                return Data.Error(AdminNotFoundFailure())//UserInvalidPhoneFailure
            }

            withContext(dispatcher) {
//                val userByEmail = userRepository.findByEmail(email)
//                if (userByEmail != null) {
//                    return@withContext if (successOnExists?.invoke(userByEmail) == true) {
//                        val userAuthorityEntities = authorityCodes.map {
//                            UserAuthorityEntity(
//                                authority = it,
//                                enableStatus = UserEnableStatus.ENABLED,
//                                activityStatus = UserActivityStatus.ACTIVE,
//                            )
//                        }.toSet()
//                        userRepository.save(userByEmail.copy(userAuthorities = userByEmail.userAuthorities.plus(userAuthorityEntities)))
//                        Data.Success(userByEmail.id)
//                    } else Data.Error(
//                        UserEmailAlreadyExistsFailure(email = email))
//                }
//                val userByPhone = userRepository.findByPhone(phone)
//                if (userByPhone != null) {
//                    return@withContext if (successOnExists?.invoke(userByPhone) == true) Data.Success(userByPhone.id) else Data.Error(
//                        UserPhoneAlreadyExistsFailure(phone = phone))
//                }
//                val request = UserRecord.CreateRequest()
//                    .setEmail(email)
//                    .setPhoneNumber(phone)
//                    .setPassword(password)
//                val record = try {
//                    firebaseAuth.createUser(request)
//                } catch (e: FirebaseAuthException) {
//                    val updateRequest = when (e.authErrorCode) {
//                        AuthErrorCode.EMAIL_ALREADY_EXISTS -> {
//                            UserRecord.UpdateRequest(firebaseAuth.getUserByEmail(email).uid)
//                                .setPhoneNumber(phone)
//                                .setPassword(password)
//                        }
//                        AuthErrorCode.PHONE_NUMBER_ALREADY_EXISTS -> {
//                            UserRecord.UpdateRequest(firebaseAuth.getUserByPhoneNumber(phone).uid)
//                                .setEmail(email)
//                                .setPassword(password)
//                        }
//                        else -> throw e
//                    }
//                    firebaseAuth.updateUser(updateRequest)
//                }
//                userRepository.findByIdOrNull(record.uid)
//                val userAuthorityEntities = authorityCodes.map {
//                    UserAuthorityEntity(
//                        authority = it,
//                        enableStatus = UserEnableStatus.ENABLED,
//                        activityStatus = UserActivityStatus.ACTIVE,
//                    )
//                }.toSet()
//                val userEntity = UserEntity(
//                    id = record.uid,
//                    email = email,
//                    phone = phone,
//                    userAuthorities = userAuthorityEntities,
//                )
//                userRepository.save(userEntity)
//                val oldUser =
//                    userRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(UserNotFoundFailure())
//
//                val avatarFile = if (avatarId != null) {
//                    fileRepository.findByIdOrNull(avatarId)
//                        ?: return@withContext Data.Error(FileNotFoundFailure())
//                } else null
//
//                val admin = AdminEntity(
//                    file = avatarFile,
//                    fullName = fullName,
//                    birthDate = birthDate,
//                    userGender = gender ?: UserGender.UNKNOWN,
//                )
//
//                userRepository.save(oldUser.copy(admin = admin))

                Data.Success("")//admin.id
            }
        } catch (e: Exception) {
            Data.Error(AdminNotFoundFailure())//CreateGeneralInfoSupplierFailure
        }
    }

    override suspend fun updateByUserId(
        adminId: String,
        updateAdminByUserIdData: UpdateAdminByUserIdData?
    ): Data<String> {
        return try {
            withContext(dispatcher) {

                val oldAdminEntity =
                    adminRepository.findByIdOrNull(adminId)
                        ?: return@withContext Data.Error(AdminNotFoundFailure())

                val avatarFile = if (updateAdminByUserIdData?.avatarId != null) {
                    fileRepository.findByIdOrNull(updateAdminByUserIdData.avatarId)
                        ?: return@withContext Data.Error(FileNotFoundFailure())
                } else null

                val newAdminEntity = oldAdminEntity.copy(
                    id = oldAdminEntity.id,
                    fullName = updateAdminByUserIdData?.fullName ?: oldAdminEntity.fullName,
                    birthDate = updateAdminByUserIdData?.birthDate ?: oldAdminEntity.birthDate,
                    gender = updateAdminByUserIdData?.gender ?: oldAdminEntity.gender,
                    file = avatarFile ?: oldAdminEntity.file
                )

                adminRepository.save(newAdminEntity)

                Data.Success("Admin updated")
            }
        } catch (e: Exception) {
            Data.Error(AdminNotFoundFailure())
        }
    }

    override suspend fun addUserClaims(userId: String, claims: Map<String, Any>): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val user = firebaseAuth.getUser(userId)
                val customClaims = mutableMapOf<String, Any>()
                customClaims.putAll(user.customClaims)
                customClaims.putAll(claims)
                firebaseAuth.setCustomUserClaims(user.uid, customClaims)
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(AdminNotFoundFailure())
        }
    }

    override suspend fun update(
        id: String,
        updateAdminData: UpdateAdminData?
    ): Data<String> {
        return try {
            withContext(dispatcher) {
//                val oldUserEntity = userRepository.findByIdOrNull(id)
//                    ?: return@withContext Data.Error(UserNotFoundFailure())
//
//                val oldAdminEntity = oldUserEntity.admin
//                    ?: return@withContext Data.Error(AdminNotFoundFailure())
//
//                val avatarFile = if (updateAdminData?.avatarId != null) {
//                    fileRepository.findByIdOrNull(updateAdminData.avatarId)
//                        ?: return@withContext Data.Error(FileNotFoundFailure())
//                } else null
//
//                val newAdminEntity = oldAdminEntity.copy(
//                    id = oldAdminEntity.id,
//                    fullName = updateAdminData?.fullName ?: oldAdminEntity.fullName,
//                    birthDate = updateAdminData?.birthDate ?: oldAdminEntity.birthDate,
//                    userGender = updateAdminData?.gender ?: oldAdminEntity.userGender,
//                    file = avatarFile ?: oldAdminEntity.file
//                )
//
//                adminRepository.save(newAdminEntity)

                Data.Success("Admin updated")
            }
        } catch (e: Exception) {
            Data.Error(AdminNotFoundFailure())
        }
    }

    override suspend fun getByUserId(userId: String): Data<Admin> {
        return try {
            withContext(dispatcher) {
                val adminEntity = adminRepository.findByIdOrNull(userId)
                    ?: return@withContext Data.Error(AdminNotFoundFailure())
                Data.Success(
                    adminEntity.admin()
                )
            }
        } catch (e: Exception) {
            Data.Error(AdminListGetFailure())
        }
    }

    override suspend fun delete(adminId: String): Data<Unit> {
        return try {
            withContext(dispatcher) {

                val adminEntity = adminRepository.findByIdOrNull(adminId)
                    ?: return@withContext Data.Error(AdminNotFoundFailure())
                adminRepository.save(
                    adminEntity.copy(
                        enableStatus = AccountEnableStatus.DISABLED
                    )
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(AdminListGetFailure())
        }
    }

    override suspend fun deleteList(ids: Set<String>): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val adminEntities = adminRepository.findAllById(ids)
                if (adminEntities.count() != ids.size) {
                    return@withContext Data.Error(AdminNotFoundFailure())
                }
                adminRepository.saveAll(
                    adminEntities.map {
                        it.copy(enableStatus = AccountEnableStatus.DISABLED)
                    }
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(AdminNotFoundFailure())
        }
    }

}