package dev.december.jeterbackend.shared.features.suppliers.data.entiies

import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
import dev.december.jeterbackend.shared.core.domain.model.*
import dev.december.jeterbackend.shared.features.authorities.data.entities.UserAuthorityEntity
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.tours.data.entities.ScheduleEntity
import org.hibernate.Hibernate
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "supplier")
data class SupplierEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "email")
    val email: String? = null,
    @Column(name = "phone")
    val phone: String? = null,
    @Column(name = "first_name")
    val name: String,
    @Column(name = "surname")
    val surname: String,
    @Column(name = "patronymic")
    val patronymic: String,
    @Column(name = "birth_date")
    val birthDate: LocalDate? = null,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name="passport_photo")
    val passportFiles: Set<FileEntity> = emptySet(),
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    val education: Set<EducationEntity>? = emptySet(),
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    val certificate: Set<CertificateEntity>? = emptySet(),
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "social_media")
    val socialMedias: SocialMediaEntity? = null,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "bank_account")
    val bankAccount: BankAccountEntity? = null,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "avatar", referencedColumnName = "id")
    val avatar: FileEntity? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "enable_status", nullable = false)
    val enableStatus: AccountEnableStatus = AccountEnableStatus.ENABLED,
    @Enumerated(EnumType.STRING)
    @Column(name = "activity_status", nullable = false)
    val activityStatus: AccountActivityStatus = AccountActivityStatus.INACTIVE,
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: SupplierStatus = SupplierStatus.ON_CHECK,
    @Column(name = "description")
    val description: String = "",
    @Column(name = "rating", nullable = false)
    val rating: Float = 0F,
    @Column(name = "feedback_count", nullable = false)
    val feedbackCount: Int = 0,
    @Column(name = "client_size", nullable = false)
    val clientSize: Int = 0,
    @Column(name = "session_count", nullable = false)
    val sessionCount: Int = 0,
    @Column(name = "total_earnings", nullable = false)
    val totalEarnings: Float = 0F,
//    @ManyToOne
//    @JoinColumn(name = "profession_id")
//    val profession: ProfessionEntity?,
    @Column(name = "experience")
    val experience: LocalDate?,
    @Column(name = "about")
    val about: String? = null,
    @Column(name = "video_file_id")
    val videoFileId: String? = null,
    @Column(name = "timezone")
    val timeZone: String? = null,
    @Column(name = "specialization_id")
    val specializationId: String? = null,
    @Column(name = "chat_per_hour")
    val chatPerHour: Int? = null,
    @Column(name = "chat_per_minute")
    val chatPerMinute: Int? = null,
    @Column(name = "audio_per_hour")
    val audioPerHour: Int? = null,
    @Column(name = "audio_per_minute")
    val audioPerMinute: Int? = null,
    @Column(name = "video_per_hour")
    val videoPerHour: Int? = null,
    @Column(name = "video_per_minute")
    val videoPerMinute: Int? = null,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "supplier_files")
    val files: Set<FileEntity> = emptySet(),
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "schedule_id")
    val schedule: ScheduleEntity? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    val userGender: Gender = Gender.UNKNOWN,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    val file: FileEntity? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "os_type")
    val osType: OsType? = null,
    @Column(name="notify")
    val notify: Boolean = true,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "registration_token")
    val registrationToken: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SupplierEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , email = $email , phone = $phone , name = $name , surname = $surname , patronymic = $patronymic , birthDate = $birthDate , socialMedias = $socialMedias , bankAccount = $bankAccount , avatar = $avatar , enableStatus = $enableStatus , activityStatus = $activityStatus , status = $status , description = $description , rating = $rating , feedbackCount = $feedbackCount , clientSize = $clientSize , sessionCount = $sessionCount , totalEarnings = $totalEarnings  , experience = $experience , about = $about , videoFileId = $videoFileId , timeZone = $timeZone , specializationId = $specializationId , chatPerHour = $chatPerHour , chatPerMinute = $chatPerMinute , audioPerHour = $audioPerHour , audioPerMinute = $audioPerMinute , videoPerHour = $videoPerHour , videoPerMinute = $videoPerMinute , schedule = $schedule , userGender = $userGender , file = $file , osType = $osType , notify = $notify , createdAt = $createdAt , updatedAt = $updatedAt , registrationToken = $registrationToken )"
    }
}
