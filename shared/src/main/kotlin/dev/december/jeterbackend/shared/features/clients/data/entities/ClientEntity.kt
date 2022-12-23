package dev.december.jeterbackend.shared.features.clients.data.entities

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.core.domain.model.*
import dev.december.jeterbackend.shared.features.authorities.data.entities.UserAuthorityEntity
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "client")
data class ClientEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "email")
    val email: String? = null,
    @Column(name = "phone")
    val phone: String? = null,
    @Column(name = "full_name")
    val fullName: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    val userGender: UserGender = UserGender.UNKNOWN,
    @Column(name = "birth_date")
    val birthDate: LocalDate? = null,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "avatar", referencedColumnName = "id")
    val avatar: FileEntity? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(name = "favorite_supplier")
    val favoriteSupplier: Set<SupplierEntity> = emptySet(),
    @Column(name = "session_count", nullable = false)
    val sessionCount: Int = 0,
    @Column(name = "expenses", nullable = false)
    val expenses: Float = 0F,
    @Enumerated(EnumType.STRING)
    @Column(name = "activity_status", nullable = false)
    val activityStatus: AccountActivityStatus = AccountActivityStatus.ACTIVE,
    @Enumerated(EnumType.STRING)
    @Column(name = "enable_status", nullable = false)
    val enableStatus: AccountEnableStatus = AccountEnableStatus.ENABLED,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    val file: FileEntity? = null,
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
    @Column(name="notify")
    val notify: Boolean = true,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "registration_token")
    val registrationToken: String? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    val userAuthorities: Set<UserAuthorityEntity> = emptySet(),
)