package dev.december.jeterbackend.supplier.features.suppliers.data.services

import com.google.firebase.auth.FirebaseAuth
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.errors.Failure
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.calendar.data.repositories.CalendarRepository
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.*
import dev.december.jeterbackend.shared.features.suppliers.domain.models.*
import dev.december.jeterbackend.shared.features.tours.data.repositories.ScheduleRepository
import dev.december.jeterbackend.shared.features.tours.data.repositories.TourRepository
import dev.december.jeterbackend.shared.features.tours.domain.models.SupplierPrice
import dev.december.jeterbackend.supplier.features.analytics.domain.services.AnalyticsCounterService
import dev.december.jeterbackend.supplier.features.suppliers.domain.errors.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SupplierServiceImpl(
    private val supplierRepository: SupplierRepository,
    private val educationRepository: EducationRepository,
    private val scheduleRepository: ScheduleRepository,
//    private val userRepository: UserRepository,
//    private val userAuthorityRepository: UserAuthorityRepository,
    private val dispatcher: CoroutineDispatcher,
    private val fileRepository: FileRepository,
//    private val professionRepository: ProfessionRepository,
    private val socialMediaRepository: SocialMediaRepository,
    private val certificateRepository: CertificateRepository,
    private val bankAccountRepository: BankAccountRepository,
    private val calendarRepository: CalendarRepository,
    private val tourRepository: TourRepository,
    private val analyticsCounterService: AnalyticsCounterService,
    private val firebaseAuth: FirebaseAuth
) : SupplierService {

    override suspend fun restore(id: String, signInProvider: String?): Data<Unit> {
        return try{
            withContext(dispatcher) {
//                if(signInProvider != "phone") {
//                    return@withContext Data.Error(SupplierOtpFailure())
//                }
//                val user = userRepository.findByIdOrNull(id) ?:
//                return@withContext Data.Error(UserNotFoundFailure())
//                supplierRepository.save(
//                    user.supplier?.copy(
//                        enableStatus = AccountEnableStatus.ENABLED,
//                        status = SupplierStatus.APPROVED,
//                    ) ?: return@withContext Data.Error(SupplierNotFoundFailure())
//                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(SupplierRestoreFailure())
        }
    }

    override suspend fun deleteByUserId(id: String, signInProvider: String?): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                if (signInProvider != "phone") {
//                    return@withContext Data.Error(SupplierOtpFailure())
//                }
//                val user = userRepository.findByIdOrNull(id) ?:
//                    return@withContext Data.Error(UserNotFoundFailure())
//                supplierRepository.save(
//                    user.supplier?.copy(
//                        enableStatus = AccountEnableStatus.DISABLED,
//                        status = SupplierStatus.DISAPPROVED,
//                    ) ?: return@withContext Data.Error(SupplierNotFoundFailure())
//                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(SupplierDeleteFailure())
        }
    }

    override suspend fun disableRoleSupplier(userId: String) {
//        val userEntity = userRepository.findByIdOrNull(userId) ?: return
//        val supplier = userEntity.supplier
//        if (supplier != null) {
//            if (supplier.enableStatus == AccountEnableStatus.ENABLED) return
//        } else {
//            userEntity.userAuthorities.map {
//                if (it.authority == AuthorityCode.SUPPLIER) {
//                    userAuthorityRepository.deleteById(it.id)
//                }
//            }
//            return
//        }
//        val userAuthorities = userEntity.userAuthorities.map {
//            if (it.authority == AuthorityCode.SUPPLIER) {
//                it.copy(
//                    enableStatus = UserEnableStatus.DISABLED,
//                    activityStatus = UserActivityStatus.INACTIVE
//                )
//            } else
//                it
//        }.toSet()
//        userRepository.save(
//            userEntity.copy(
//                userAuthorities = userAuthorities,
//            )
//        )
    }

    override suspend fun createSupplier(
        email: String,
        phone: String,
        password: String,
        generalInfo: SupplierGeneralInfo,
        education: List<SupplierEducation>,
        certificate: List<SupplierCertificate>,
        socialMedia: SupplierSocialMedia?,
        bankAccount: SupplierBankAccount?,
        avatar: File?,
        schedule: SupplierSchedule?,
        price: SupplierPrice?,
        files: List<File>?,
        registrationToken: String?,
        successOnExists: ((SupplierEntity) -> Boolean)?
    ): Data<String> {
        return try {
//            if (!StringUtils.hasText(email) && !StringUtils.hasText(phone) || !email.contains("@") || !email.contains(".")) {
//                return Data.Error(UserInvalidIdentityFailure())
//            }
//
//            if (!StringUtils.hasText(password) || (StringUtils.hasText(password) && password.length < 8)) {
//                return Data.Error(UserInvalidPasswordFailure())
//            }
//
//            if (phone.indexOf("+") != 0 || phone.length < 12) {
//                return Data.Error(UserInvalidPhoneFailure())
            withContext(dispatcher) {
//
//               val userByEmail = userRepository.findByEmail(email)
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

//                val professionEntity = generalInfo.professionId?.run { professionRepository.findByIdOrNull(this) }
//
//                var passportFiles = emptySet<FileEntity>()
//                generalInfo.passportFiles.forEach {
//                    passportFiles = passportFiles.plus(fileRepository.findByIdOrNull(it.id) ?: return@withContext Data.Error(FileNotFoundFailure())) }
//
//
//                val avatarEntity = if (avatar != null) {
//                    fileRepository.findByIdOrNull(avatar.id) ?: return@withContext Data.Error(FileNotFoundFailure())
//                } else null
//
//                val fileEntities = files?.let { fileRepository.findAllById(it.map { file -> file.id }) }
//
//                val supplier = SupplierEntity(
//                    id = record.id
//                    name = generalInfo.name,
//                    surName = generalInfo.surName,
//                    patronymic = generalInfo.patronymic ?: "",
//                    birthDate = generalInfo.birthDate,
//                    passportFiles = passportFiles,
//                    profession = professionEntity,
//                    avatar = avatarEntity,
//                    experience = generalInfo.experience,
//                    about = generalInfo.about,
//                    videoFileId = generalInfo.videoFileId,
//                    timeZone = generalInfo.timeZone,
//                    specializationId = generalInfo.specializationId,
//                    chatPerHour = price?.chatPerHour,
//                    chatPerMinute = price?.chatPerMinute,
//                    audioPerHour = price?.audioPerHour,
//                    audioPerMinute = price?.audioPerMinute,
//                    videoPerHour = price?.videoPerHour,
//                    videoPerMinute = price?.videoPerMinute,
//                    files = fileEntities?.toSet() ?: emptySet(),
//                    userGender = generalInfo.gender ?: UserGender.UNKNOWN,
//                )
//// TODO: 06.10.2022 сделать проверку на наличие одного
//
//                var newSupplierEducation = emptySet<EducationEntity>()
//                education.map{ education ->
//                    val educationFile = fileRepository.findByIdOrNull(education.file.id)
//                        ?: return@withContext Data.Error(FileNotFoundFailure())
//                    newSupplierEducation= newSupplierEducation.plus(
//                        educationRepository.save(EducationEntity(
//                            institutionName = education.institutionName,
//                            graduationYear = education.graduationYear,
//                            faculty = education.faculty,
//                            file = educationFile,
//                        ))
//                    )
//                }
//
//                var newSupplierCertificate = emptySet<CertificateEntity>()
//                certificate.forEach { certificate ->
//                    val certificateFile = fileRepository.findByIdOrNull(certificate.file.id)
//                        ?: return@withContext Data.Error(FileNotFoundFailure())
//
//                    newSupplierCertificate =  newSupplierCertificate.plus(
//                        certificateRepository.save(CertificateEntity(
//                            title = certificate.title,
//                            issueDate = certificate.issueDate,
//                            specialization = certificate.specialization,
//                            file = certificateFile,
//                        ))
//                    )
//                }
//
//                val scheduleNew = if (schedule == null) {
//                    //TODO change to nullables
//                    ScheduleEntity(
//                        monday = ScheduleEntity.defaultWorkPeriod,
//                        mondayBreak = ScheduleEntity.defaultBreakPeriod,
//                        tuesday = ScheduleEntity.defaultWorkPeriod,
//                        tuesdayBreak = ScheduleEntity.defaultBreakPeriod,
//                        wednesday = ScheduleEntity.defaultWorkPeriod,
//                        wednesdayBreak = ScheduleEntity.defaultBreakPeriod,
//                        thursday = ScheduleEntity.defaultWorkPeriod,
//                        thursdayBreak = ScheduleEntity.defaultBreakPeriod,
//                        friday = ScheduleEntity.defaultWorkPeriod,
//                        fridayBreak = ScheduleEntity.defaultBreakPeriod,
//                        saturday = ScheduleEntity.defaultWorkPeriod,
//                        saturdayBreak = ScheduleEntity.defaultBreakPeriod,
//                        sunday = ScheduleEntity.defaultWorkPeriod,
//                        sundayBreak = ScheduleEntity.defaultBreakPeriod
//                    )
//                } else {
//                    ScheduleEntity(
//                        monday = schedule.monday,
//                        mondayBreak = schedule.mondayBreak,
//                        tuesday = schedule.tuesday,
//                        tuesdayBreak = schedule.tuesdayBreak,
//                        wednesday = schedule.wednesday,
//                        wednesdayBreak = schedule.wednesdayBreak,
//                        thursday = schedule.thursday,
//                        thursdayBreak = schedule.thursdayBreak,
//                        friday = schedule.friday,
//                        fridayBreak = schedule.fridayBreak,
//                        saturday = schedule.saturday,
//                        saturdayBreak = schedule.saturdayBreak,
//                        sunday = schedule.sunday,
//                        sundayBreak = schedule.sundayBreak
//                    )
//                }
//
//                val socialMedias = if (socialMedia != null) SocialMediaEntity(
//                    linkedin = socialMedia.linkedin,
//                    facebook = socialMedia.facebook,
//                    instagram = socialMedia.instagram,
//                    youtube = socialMedia.youtube,
//                    tiktok = socialMedia.tiktok,
//                    telegram = socialMedia.telegram,
//                    twitter = socialMedia.twitter,
//                    vk = socialMedia.vk
//                ) else null
//
//                val newBankAccount = if (bankAccount != null) BankAccountEntity(
//                    cardNumber = bankAccount.cardNumber,
//                    companyName = bankAccount.companyName,
//                    companyAddress = bankAccount.companyAddress,
//                    bin = bankAccount.bin,
//                    kbe = bankAccount.kbe,
//                    bik = bankAccount.bik,
//                    iik = bankAccount.iik
//                ) else null
//
//                val savedSupplier = supplier.copy(schedule = scheduleNew, education = newSupplierEducation,
//                    certificate = newSupplierCertificate, socialMedias = socialMedias, bankAccount = newBankAccount)
//
//                val newUserEntity = userEntity.copy(
//                    updatedAt = LocalDateTime.now(),
//                    supplier = savedSupplier,
//                )
//
//                userRepository.save(newUserEntity)
//                analyticsCounterService.countCreate()
                Data.Success("")//supplier.id
            }
        } catch (e: Exception) {
            Data.Error(CreateGeneralInfoSupplierFailure())
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
            Data.Error(SupplierNotFoundFailure())
        }
    }

    override suspend fun get(userId: String): Data<String> {//<Supplier>
        return try {
            withContext(dispatcher) {
//
//                val userEntity = userRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(UserNotFoundFailure())
//                val supplierEntity = userEntity.supplier?.supplier() ?: return@withContext Data.Error(SupplierNotFoundFailure())
                Data.Success("")//supplierEntity
            }
        } catch (e: Exception) {
            Data.Error(SupplierGetFailure())
        }
    }

    override suspend fun getById(supplierId: String): Data<String> {//<Supplier>
        return try {
            withContext(dispatcher) {
//
//                val supplierEntity = supplierRepository.findByIdOrNull(supplierId) ?: return@withContext Data.Error(
//                    SupplierNotFoundFailure()
//                )
                Data.Success("")//supplierEntity.supplier()
            }
        } catch (e: Exception) {
            Data.Error(SupplierGetFailure())
        }
    }

    override suspend fun updateSchedule(userId: String, schedule: SupplierSchedule): Data<String> {
        return try {
//            val supplierEntity =
//                userRepository.findByIdOrNull(userId)?.supplier ?: return Data.Error(SupplierNotFoundFailure())
//            val oldSchedule = supplierEntity.schedule
//
//            val newSchedule = ScheduleEntity(
//                id = oldSchedule!!.id,
//                supplier = supplierEntity,
//                monday = schedule.monday,
//                mondayBreak = schedule.mondayBreak,
//                tuesday = schedule.tuesday,
//                tuesdayBreak = schedule.tuesdayBreak,
//                wednesday = schedule.wednesday,
//                wednesdayBreak = schedule.wednesdayBreak,
//                thursday = schedule.thursday,
//                thursdayBreak = schedule.thursdayBreak,
//                friday = schedule.friday,
//                fridayBreak = schedule.fridayBreak,
//                saturday = schedule.saturday,
//                saturdayBreak = schedule.saturdayBreak,
//                sunday = schedule.sunday,
//                sundayBreak = schedule.sundayBreak,
//            )
//
//            scheduleRepository.save(newSchedule)

            Data.Success("")

        } catch (e: Exception) {
            Data.Error(SupplierSchedulePatchFailure())
        }
    }

    override suspend fun addEducation(userId: String, education: SupplierEducation): Data<String> {
        return try {
            withContext(dispatcher) {
//                val supplierEntity =
//                    userRepository.findByIdOrNull(userId)?.supplier ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val file =
//                    fileRepository.findByIdOrNull(education.file.id) ?: return@withContext Data.Error(FileNotFoundFailure())
//
//                val newEducation = EducationEntity(
//                    institutionName = education.institutionName,
//                    graduationYear = education.graduationYear,
//                    faculty = education.faculty,
//                    file = file,
//                    supplier = supplierEntity,
//                )
//                educationRepository.save(newEducation)

                Data.Success("")
            }
        } catch (e: Exception) {
            Data.Error(SupplierEducationAddFailure())
        }
    }

    override suspend fun deleteEducation(supplier: Supplier, educationId: String): Data<String> {
        return try {
            withContext(dispatcher) {
//                val educationEntity =
//                    educationRepository.findByIdOrNull(educationId) ?: return@withContext Data.Error(SupplierEducationNotFoundFailure())
//                supplier.education?.forEach {
//                    if (it.id == educationEntity.id) {
//                        educationRepository.deleteById(it.id)
//                    }
//                }
                Data.Success("")
            }
        } catch (e: Exception) {
            Data.Error(SupplierEducationUpdateFailure())
        }
    }

    override suspend fun updateEducation(supplier: Supplier, educationId: String, data: SupplierEducationUpdate): Data<String> {
        return try {
            withContext(dispatcher) {
//                val oldEducation =
//                    educationRepository.findByIdOrNull(educationId) ?: return@withContext Data.Error(SupplierEducationNotFoundFailure())
//                val file =
//                    data.fileId?.let { fileRepository.findByIdOrNull(it.id) }
//
//                if (supplier.education?.contains(oldEducation.education()) == true) {
//                    val updateEdu = oldEducation.copy(
//                        institutionName = data.institutionName ?: oldEducation.institutionName,
//                        graduationYear = data.graduationYear ?: oldEducation.graduationYear,
//                        faculty = data.faculty ?: oldEducation.faculty,
//                        file = file ?: oldEducation.file
//                    )
//                    educationRepository.save(updateEdu)
//                }
                Data.Success("")
            }
        } catch (e: Exception) {
            Data.Error(SupplierEducationAddFailure())
        }
    }

    override suspend fun addCertificate(supplier: Supplier, certificate: SupplierCertificate): Data<String> {
        return try {
            withContext(dispatcher) {
//                val supplierEntity =
//                    supplierRepository.findByIdOrNull(supplier.id) ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val file =
//                    fileRepository.findByIdOrNull(certificate.file.id) ?: return@withContext Data.Error(FileNotFoundFailure())
//
//                val newCertificate = CertificateEntity(
//                    title = certificate.title,
//                    issueDate = certificate.issueDate,
//                    specialization = certificate.specialization,
//                    file = file,
//                    supplier = supplierEntity,
//                )
//                certificateRepository.save(newCertificate)

                Data.Success("")
            }
        } catch (e: Exception) {
            Data.Error(SupplierCertificateAddFailure())
        }
    }

    override suspend fun deleteCertificate(supplier: Supplier, certificateId: String): Data<String> {
        return try {
            withContext(dispatcher) {
//                val certificateEntity =
//                    certificateRepository.findByIdOrNull(certificateId) ?: return@withContext Data.Error(SupplierCertificateNotFoundFailure())
//                supplier.certificate?.forEach {
//                    if (it.id == certificateEntity.id) {
//                        certificateRepository.deleteById(it.id)
//                    }
//                }
                Data.Success("")
            }
        } catch (e: Exception) {
            Data.Error(SupplierCertificateDeleteFailure())
        }
    }

    override suspend fun updateCertificate(supplier: Supplier, certificateId: String, data: SupplierCertificateUpdate): Data<String> {
        return try {
            withContext(dispatcher) {
//                val oldCertificate =
//                    certificateRepository.findByIdOrNull(certificateId) ?: return@withContext Data.Error(SupplierCertificateNotFoundFailure())
//                val file =
//                    data.fileId?.let { fileRepository.findByIdOrNull(it.id) }
//
//                if (supplier.certificate?.contains(oldCertificate.certificate()) == true) {
//                    val updateCertificate = oldCertificate.copy(
//                        title = data.title ?: oldCertificate.title,
//                        issueDate = data.issueDate ?: oldCertificate.issueDate,
//                        specialization = data.specialization ?: oldCertificate.specialization,
//                        file = file ?: oldCertificate.file
//                    )
//                    certificateRepository.save(updateCertificate)
//                }
                Data.Success("")
            }
        } catch (e: Exception) {
            Data.Error(SupplierCertificateUpdateFailure())
        }
    }

    override suspend fun updateGeneralInfo(userId: String, generalInfo: SupplierGeneralInfoUpdate): Data<String> {
        return try {
            withContext(dispatcher) {

//                val oldUserEntity =
//                    userRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(UserNotFoundFailure())
//
//                val supplierEntity = oldUserEntity.supplier ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                generalInfo.passportFiles?.forEach {fileRepository.findByIdOrNull(it.id) ?: return@withContext Data.Error(FileNotFoundFailure()) }
//                val passportFiles = generalInfo.passportFiles?.let { files -> fileRepository.findAllById(files.map { file ->  file.id }) }
//
//                val avatar = if (generalInfo.avatar != null) {
//                    fileRepository.findByIdOrNull(generalInfo.avatar!!.id)
//                } else null
//
//
//                val updatedGeneralInfo = supplierEntity.copy(
//                    name = generalInfo.name ?: supplierEntity.name,
//                    surName = generalInfo.surName ?: supplierEntity.surName,
//                    patronymic = generalInfo.patronymic ?: supplierEntity.patronymic,
//                    birthDate = generalInfo.birthDate ?: supplierEntity.birthDate,
//                    passportFiles = passportFiles?.toSet() ?: supplierEntity.passportFiles,
//                    avatar = avatar ?: supplierEntity.avatar,
//                    experience = generalInfo.experience ?: supplierEntity.experience,
//                    about = generalInfo.about ?: supplierEntity.about,
//                    videoFileId = generalInfo.videoFileId ?: supplierEntity.videoFileId,
//                    timeZone = generalInfo.timeZone ?: supplierEntity.timeZone,
//                    specializationId = generalInfo.specializationId ?: supplierEntity.specializationId,
//                    userGender = generalInfo.gender ?: supplierEntity.userGender
//                )
//                supplierRepository.save(updatedGeneralInfo)

                Data.Success("")
            }


        } catch (e: Exception) {
            Data.Error(SupplierSchedulePatchFailure())
        }
    }


    override suspend fun updatePrice(userId: String, price: SupplierPrice): Data<String> {
        return try {
            withContext(dispatcher) {
//                val supplierEntity = userRepository.findByIdOrNull(userId)?.supplier ?: return@withContext Data.Error(
//                    SupplierNotFoundFailure()
//                )
//
//                val newSupplier = supplierEntity.copy(
//                    chatPerHour = price.chatPerHour,
//                    chatPerMinute = price.chatPerMinute,
//                    audioPerHour = price.audioPerHour,
//                    audioPerMinute = price.audioPerMinute,
//                    videoPerHour = price.videoPerHour,
//                    videoPerMinute = price.videoPerMinute
//                )
//
//                supplierRepository.save(newSupplier)

                Data.Success("")
            }

        } catch (e: Exception) {
            Data.Error(SupplierSchedulePatchFailure())
        }
    }

    override suspend fun updateSocialMedia(userId: String, socialMedia: SupplierSocialMedia): Data<String> {
        return try {
//            val supplierEntity = userRepository.findByIdOrNull(userId)?.supplier ?: return Data.Error(SupplierNotFoundFailure())
//
//            val oldMedia = socialMediaRepository.findBySupplierId(supplierEntity.id)
//            if (oldMedia != null) {
//                socialMediaRepository.save(oldMedia.copy(
//                    linkedin = socialMedia.linkedin ?: oldMedia.linkedin,
//                    facebook = socialMedia.facebook ?: oldMedia.facebook,
//                    instagram = socialMedia.instagram ?: oldMedia.instagram,
//                    youtube = socialMedia.youtube ?: oldMedia.youtube,
//                    tiktok = socialMedia.tiktok ?: oldMedia.tiktok,
//                    telegram = socialMedia.telegram ?: oldMedia.telegram,
//                    twitter = socialMedia.twitter ?: oldMedia.twitter,
//                    vk = socialMedia.vk ?: oldMedia.vk
//                ))
//            } else {
//                val newMedia = SocialMediaEntity(
//                    linkedin = socialMedia.linkedin,
//                    facebook = socialMedia.facebook,
//                    instagram = socialMedia.instagram,
//                    youtube = socialMedia.youtube,
//                    tiktok = socialMedia.tiktok,
//                    telegram = socialMedia.telegram,
//                    twitter = socialMedia.twitter,
//                    vk = socialMedia.vk
//                )
//                socialMediaRepository.save(newMedia)
//                supplierRepository.save(supplierEntity.copy(
//                    socialMedias = newMedia
//                ))
//            }

            Data.Success("")
        } catch (e: Exception){
            Data.Error(SupplierSocialMediaPatchFailure())
        }
    }

    override suspend fun toggleNotification(userId: String, toggleStatus: Boolean): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                val oldSupplierEntity =
//                    (userRepository.findByIdOrNull(userId)
//                        ?: return@withContext Data.Error(UserNotFoundFailure())
//                    ).supplier ?: return@withContext Data.Error(SupplierNotFoundFailure())
//                val newEntity = oldSupplierEntity.copy(
//                    notify = toggleStatus
//                )
//                supplierRepository.save(newEntity)
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(SupplierToggleNotificationFailure())
        }
    }

    override suspend fun getByPhone(phone: String): Data<Boolean> {
        return try {
            withContext(dispatcher) {
//                val supplierEntity = userRepository.findByPhone(phone)?.supplier
                Data.Success(false)//supplierEntity != null
            }
        } catch (e: Exception) {
            Data.Error(SupplierGetFailure())
        }
    }

    override suspend fun getCalendar(userId: String, firstDayOfMonth: LocalDate): Data<String> {//Calendar
        return try {
            withContext(dispatcher) {
//                val supplierEntity = (userRepository.findByIdOrNull(userId) ?:
//                                        return@withContext Data.Error(UserNotFoundFailure())
//                                    ).supplier
//                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
//                println(supplierEntity.id )
//                val calendarEntity = calendarRepository.findBySupplierIdAndFirstDayOfMonth(
//                    supplierEntity.id, firstDayOfMonth
//                ) ?: return@withContext Data.Error(CalendarNotFoundFailure())
                Data.Success("")//calendarEntity.convert()
            }
        } catch (e: Exception) {
            Data.Error(CalendarGetFailure())
        }
    }

    override suspend fun updateCalendar(
        userId: String,
        firstDayOfMonth: LocalDate,
        workingDays: Set<LocalDate>,
    ): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                val supplierEntity = (userRepository.findByIdOrNull(userId) ?:
//                            return@withContext Data.Error(UserNotFoundFailure())
//                        ).supplier
//                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val lastDayOfMonth = firstDayOfMonth.withDayOfMonth(
//                    firstDayOfMonth.month.length(firstDayOfMonth.isLeapYear)
//                )
//
//                val appointmentEntities = appointmentRepository.findBySupplierIdAndAppointmentStatusInAndReservationDateBetween(
//                    supplierEntity.id,
//                    setOf(
//                        AppointmentStatus.SUPPLIER_SUBMITTED,
//                        AppointmentStatus.CLIENT_SUBMITTED,
//                        AppointmentStatus.CONFIRMED,
//                    ),
//                    firstDayOfMonth.atStartOfDay(),
//                    lastDayOfMonth.atTime(23, 59,59, 999)
//                )
//
//                appointmentEntities.forEach{
//                    if (!workingDays.contains(it.reservationDate.toLocalDate())) {
//                        return@withContext Data.Error(AppointmentsExistAtGivenDateFailure(
//                            message = "Appointments exist on ${it.reservationDate.toLocalDate()}!"
//                        ))
//                    }
//                }
//
//                val oldEntity = calendarRepository.findBySupplierIdAndFirstDayOfMonth(
//                    supplierEntity.id, firstDayOfMonth
//                )
//
//                val calendarEntity = oldEntity?.copy(
//                    workingDays = workingDays.toList(),
//                ) ?: CalendarEntity(
//                    firstDayOfMonth = firstDayOfMonth,
//                    workingDays = workingDays.toList(),
//                    supplier = supplierEntity
//                )
//
//                calendarRepository.save(calendarEntity)

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            println(e)
            Data.Error(CalendarCreationFailure())
        }
    }


    override suspend fun updateBankAccount(userId: String, bankAccount: SupplierBankAccount): Data<String> {
        return try {
//            val supplierEntity = userRepository.findByIdOrNull(userId)?.supplier ?: return Data.Error(SupplierNotFoundFailure())
//
//            val oldBankAccount = bankAccountRepository.findBySupplierId(supplierEntity.id)
//            if (oldBankAccount != null) {
//                bankAccountRepository.save(oldBankAccount.copy(
//                    cardNumber = bankAccount.cardNumber ?: oldBankAccount.cardNumber,
//                    companyName = bankAccount.companyName ?: oldBankAccount.companyName,
//                    companyAddress = bankAccount.companyAddress ?: oldBankAccount.companyAddress,
//                    bin = bankAccount.bin ?: oldBankAccount.bin,
//                    kbe = bankAccount.kbe ?: oldBankAccount.kbe,
//                    bik = bankAccount.bik ?: oldBankAccount.bik,
//                    iik = bankAccount.iik ?: oldBankAccount.iik
//                ))
//            } else {
//                val newBankAccount = BankAccountEntity(
//                    cardNumber = bankAccount.cardNumber,
//                    companyName = bankAccount.companyName,
//                    companyAddress = bankAccount.companyAddress,
//                    bin = bankAccount.bin,
//                    kbe = bankAccount.kbe,
//                    bik = bankAccount.bik,
//                    iik = bankAccount.iik
//                )
//                bankAccountRepository.save(newBankAccount)
//                supplierRepository.save(supplierEntity.copy(
//                    bankAccount = newBankAccount
//                ))
//            }

            Data.Success("")
        } catch (e: Exception) {
            Data.Error(SupplierBankAccountPatchFailure())
        }
    }

    override suspend fun updateVideoPrice(userId: String, price: Int?): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                val supplierEntity = (userRepository.findByIdOrNull(userId) ?:
//                return@withContext Data.Error(UserNotFoundFailure())
//                        ).supplier
//                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                supplierRepository.save(
//                    supplierEntity.copy(
//                        videoPerHour = price,
//                        activityStatus = (if (price == null) AccountActivityStatus.INACTIVE else AccountActivityStatus.ACTIVE)
//                    )
//                )

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            println(e)
            Data.Error(SupplierVideoPriceUpdateFailure())
        }
    }

    override suspend fun updateAudioPrice(userId: String, price: Int?): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                val supplierEntity = (userRepository.findByIdOrNull(userId) ?:
//                return@withContext Data.Error(UserNotFoundFailure())
//                        ).supplier
//                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                supplierRepository.save(
//                    supplierEntity.copy(
//                        audioPerHour = price,
//                        activityStatus = (if (price == null) AccountActivityStatus.INACTIVE else AccountActivityStatus.ACTIVE)
//                    )
//                )

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            println(e)
            Data.Error(SupplierAudioPriceUpdateFailure())
        }
    }


    override suspend fun updateRegistrationToken(userId: String, registrationToken: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                val supplierEntity = (userRepository.findByIdOrNull(userId) ?:
//                return@withContext Data.Error(UserNotFoundFailure())
//                        ).supplier
//                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                supplierRepository.save(
//                    supplierEntity.copy(
//                        registrationToken = registrationToken
//                    )
//                )

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(SupplierRegistrationTokenUpdateFailure())
        }
    }
    override suspend fun updateDescription(userId: String, description: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                val supplierEntity = (userRepository.findByIdOrNull(userId) ?:
//                return@withContext Data.Error(UserNotFoundFailure())
//                        ).supplier
//                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                supplierRepository.save(
//                    supplierEntity.copy(
//                        description = description
//                    )
//                )

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            println(e)
            Data.Error(SupplierAudioPriceUpdateFailure())
        }
    }

    private fun checkSupplierFieldsForActivityStatus(
        supplier: SupplierEntity,
        firstDayOfMonth: LocalDate,
    ): Failure? {
        if (supplier.name == "" || supplier.surname == "" || supplier.patronymic == "") {
            return SupplierFullNameIsRequiredFailure()
        }
        if (supplier.education == null || supplier.education?.size == 0) {
            return SupplierEducationNotFoundFailure()
        }
        // TODO: 30.11.2022 вернуть проверку
//        if (supplier.bankAccount == null) {
//            return SupplierBankAccountIsRequiredFailure()
//        }
        if (supplier.videoPerHour == null) {
            return SupplierVideoPriceIsRequiredFailure()
        }
        if (supplier.audioPerHour == null) {
            return SupplierAudioPriceIsRequiredFailure()
        }
        val calendar = calendarRepository.findBySupplierIdAndFirstDayOfMonth(supplier.id, firstDayOfMonth)
            ?: return CalendarNotFoundFailure()
        return null
    }

    override suspend fun updateActivityStatus(
        userId: String,
        activityStatus: AccountActivityStatus,
        firstDayOfMonth: LocalDate,
    ): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                val supplierEntity = (userRepository.findByIdOrNull(userId) ?:
//                return@withContext Data.Error(UserNotFoundFailure())
//                        ).supplier
//                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val failure = checkSupplierFieldsForActivityStatus(supplierEntity, firstDayOfMonth)
//
//                if (failure != null) {
//                    return@withContext Data.Error(failure)
//                }
//
//                supplierRepository.save(
//                    supplierEntity.copy(
//                        activityStatus = activityStatus
//                    )
//                )

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            println(e)
            Data.Error(UpdateSupplierActivityStatusFailure())
        }
    }

    override suspend fun resetActivityStatus(): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val suppliers = supplierRepository.findAllByActivityStatusAndEnableStatusAndStatus(
                    AccountActivityStatus.ACTIVE,
                    AccountEnableStatus.ENABLED,
                    SupplierStatus.APPROVED
                )
                suppliers.forEach { supplier ->
                    val failure = checkSupplierFieldsForActivityStatus(supplier, LocalDate.now())
                    if (failure != null) {
                        supplierRepository.save(
                            supplier.copy(
                                activityStatus = AccountActivityStatus.INACTIVE
                            )
                        )
                    }
                }
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            println(e)
            Data.Error(UpdateSupplierActivityStatusFailure())
        }
    }

    override suspend fun updateExperience(userId: String, experience: LocalDate?): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                val supplierEntity = (userRepository.findByIdOrNull(userId) ?:
//                return@withContext Data.Error(UserNotFoundFailure())
//                        ).supplier
//                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                supplierRepository.save(
//                    supplierEntity.copy(
//                        experience = experience
//                    )
//                )

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            println(e)
            Data.Error(UpdateSupplierActivityStatusFailure())
        }
    }
}