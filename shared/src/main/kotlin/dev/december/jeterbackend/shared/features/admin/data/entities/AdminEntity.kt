package dev.december.jeterbackend.shared.features.admin.data.entities

import dev.december.jeterbackend.shared.core.domain.model.*
import dev.december.jeterbackend.shared.features.authorities.data.entities.UserAuthorityEntity
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "admin")
data class AdminEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "full_name")
    val fullName: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    val gender: Gender = Gender.UNKNOWN,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    val file: FileEntity? = null,
    @Column(name = "birth_date")
    val birthDate: LocalDate? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "activity_status", nullable = false)
    val activityStatus: AccountActivityStatus = AccountActivityStatus.ACTIVE,
    @Enumerated(EnumType.STRING)
    @Column(name = "enable_status", nullable = false)
    val enableStatus: AccountEnableStatus = AccountEnableStatus.ENABLED,
    @Column(name = "phone_type")
    val phoneType: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "os_type")
    val osType: OsType? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "app_type")
    val appType: AppType? = null,
    @Column(name = "have_unread_messages")
    val haveUnreadMessages: Boolean? = false,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    val userAuthorities: Set<UserAuthorityEntity> = emptySet(),
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)