package dev.december.jeterbackend.admin.features.admin.data.services

import com.google.firebase.auth.AuthErrorCode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserRecord
import dev.december.jeterbackend.admin.features.admin.domain.errors.AdminDeleteFailure
import dev.december.jeterbackend.admin.features.admin.domain.errors.AdminListGetFailure
import dev.december.jeterbackend.admin.features.admin.domain.services.AdminService
import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminByUserIdData
import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminData
import dev.december.jeterbackend.admin.features.admin.domain.errors.AdminNotFoundFailure
import dev.december.jeterbackend.admin.features.files.domain.error.FileNotFoundFailure
import dev.december.jeterbackend.admin.features.suppliers.domain.errors.CreateGeneralInfoSupplierFailure
import dev.december.jeterbackend.admin.features.suppliers.domain.errors.SupplierGetFailure
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.domain.model.Gender
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.admin.data.entities.AdminEntity
import dev.december.jeterbackend.shared.features.admin.data.entities.extensions.AdminAuthorityEntity
import dev.december.jeterbackend.shared.features.admin.data.entities.extensions.admin
import dev.december.jeterbackend.shared.features.admin.data.repositories.AdminRepository
import dev.december.jeterbackend.shared.features.admin.data.repositories.specifications.AdminSpecification
import dev.december.jeterbackend.shared.features.admin.domain.models.Admin
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminAuthorityCode
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminSortField
import dev.december.jeterbackend.shared.features.authorities.domain.models.AuthorityCode
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import dev.december.jeterbackend.shared.features.user.domain.errors.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.slf4j.Logger
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
    private val firebaseAuth: FirebaseAuth,
    private val adminRepository: AdminRepository,
    private val fileRepository: FileRepository,
    private val dispatcher: CoroutineDispatcher,
    private val logger: Logger
) : AdminService {

    override suspend fun getList(
        sortField: AdminSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        searchField: String?,
        authorityCodes: Set<AdminAuthorityCode>?,
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
                        .and(AdminSpecification.adminAuthorityJoinFilter(authorityCodes))

                val entities = adminRepository.findAll(specifications, pageable)

                val admins = entities.map { it.admin() }
                Data.Success(admins)
            }
        } catch (e: Exception) {
            logger.error(e.message)
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
        authorityCodes: Set<AuthorityCode>,
        successOnExists: ((AdminEntity) -> Boolean)?,
    ): Data<String> {
        return try {
            if (!StringUtils.hasText(email) && !StringUtils.hasText(phone) || !email.contains("@") || !email.contains(".")) {
                return Data.Error(UserInvalidIdentityFailure())
            }

            if (!StringUtils.hasText(password) || (StringUtils.hasText(password) && password.length < 8)) {
                return Data.Error(UserInvalidPasswordFailure())
            }

            if (phone.indexOf("+") != 0 || phone.length < 12) {
                return Data.Error(UserInvalidPhoneFailure())
            }

            withContext(dispatcher) {
                val userByEmail = adminRepository.findByEmail(email)
                if (userByEmail != null) {
                    return@withContext if (successOnExists?.invoke(userByEmail) == true) {
                        val adminAuthorityEntities = authorityCodes.map {
                            AdminAuthorityEntity(
                                authority = it,
                            )
                        }.toSet()
                        adminRepository.save(userByEmail.copy(
                            adminAuthorities = userByEmail.adminAuthorities.plus(adminAuthorityEntities)
                        )
                        )
                        Data.Success(userByEmail.id)
                    } else {
                        return@withContext Data.Error(
                            UserEmailAlreadyExistsFailure(email = email)
                        )
                    }
                }

                val userByPhone = adminRepository.findByPhone(phone)
                if (userByPhone != null) {
                    return@withContext if (successOnExists?.invoke(userByPhone) == true) {
                        val adminAuthorityEntities = authorityCodes.map {
                            AdminAuthorityEntity(
                                authority = it,
                            )
                        }.toSet()
                        adminRepository.save(userByPhone.copy(
                            adminAuthorities = userByPhone.adminAuthorities.plus(adminAuthorityEntities))
                        )
                        Data.Success(userByPhone.id)
                    } else {
                        return@withContext Data.Error(
                            UserPhoneAlreadyExistsFailure(phone = phone)
                        )
                    }
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

                val avatarFile = if (avatarId != null) {
                    fileRepository.findByIdOrNull(avatarId)
                        ?: return@withContext Data.Error(FileNotFoundFailure())
                } else null
                val adminAuthorityEntities = authorityCodes.map {
                    AdminAuthorityEntity(
                        authority = it,
                    )
                }.toSet()

                val admin = AdminEntity(
                    id = record.uid,
                    email = email,
                    phone = phone,
                    file = avatarFile,
                    fullName = fullName,
                    birthDate = birthDate,
                    userGender = gender ?: Gender.UNKNOWN,
                    adminAuthorities = adminAuthorityEntities
                )

                adminRepository.save(admin)

                Data.Success(admin.id)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(CreateGeneralInfoSupplierFailure())
        }
    }

    override suspend fun updateById(
        adminId: String,
        updateAdminData: UpdateAdminData?
    ): Data<String> {
        return try {
            withContext(dispatcher) {

                val oldAdminEntity =
                    adminRepository.findByIdOrNull(adminId)
                        ?: return@withContext Data.Error(AdminNotFoundFailure())

                val avatarFile = if (updateAdminData?.avatarId != null) {
                    fileRepository.findByIdOrNull(updateAdminData.avatarId)
                        ?: return@withContext Data.Error(FileNotFoundFailure())
                } else null

                val newAdminEntity = oldAdminEntity.copy(
                    id = oldAdminEntity.id,
                    fullName = updateAdminData?.fullName ?: oldAdminEntity.fullName,
                    birthDate = updateAdminData?.birthDate ?: oldAdminEntity.birthDate,
                    userGender = updateAdminData?.gender ?: oldAdminEntity.userGender,
                    file = avatarFile ?: oldAdminEntity.file
                )

                adminRepository.save(newAdminEntity)

                Data.Success("Admin updated")
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(SupplierGetFailure())
        }
    }

    override suspend fun getById(adminId: String): Data<Admin> {
        return try {
            withContext(dispatcher) {
                val adminEntity = adminRepository.findByIdOrNull(adminId)
                    ?: return@withContext Data.Error(AdminNotFoundFailure())
                Data.Success(
                    adminEntity.admin()
                )
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(AdminListGetFailure())
        }
    }

    override suspend fun delete(adminId: String): Data<Unit> {
        return try {
            withContext(dispatcher) {

                val adminEntity = adminRepository.findByIdOrNull(adminId)
                    ?: return@withContext Data.Error(UserNotFoundFailure())
                adminRepository.save(
                    adminEntity.copy(
                        enableStatus = AccountEnableStatus.DISABLED
                    )
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(AdminListGetFailure())
        }
    }

    override suspend fun deleteList(ids: Set<String>): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val adminEntities = adminRepository.findAllById(ids)
                if (adminEntities.count() != ids.size) {
                    return@withContext Data.Error(UserNotFoundFailure())
                }
                adminRepository.saveAll(
                    adminEntities.map {
                        it.copy(enableStatus = AccountEnableStatus.DISABLED)
                    }
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(AdminDeleteFailure())
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
            logger.error(e.message)
            Data.Error(UserNotFoundFailure())
        }
    }
}