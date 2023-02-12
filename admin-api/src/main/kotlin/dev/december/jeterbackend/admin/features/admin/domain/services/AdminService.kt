package dev.december.jeterbackend.admin.features.admin.domain.services

import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminByUserIdData
import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminData
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.domain.model.Gender
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.admin.data.entities.AdminEntity
import dev.december.jeterbackend.shared.features.admin.domain.models.Admin
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminAuthorityCode
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminSortField
import dev.december.jeterbackend.shared.features.authorities.domain.models.AuthorityCode
import org.springframework.data.domain.Page
import java.time.LocalDate
import java.time.LocalDateTime

interface AdminService {

    suspend fun getList(
        sortField: AdminSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        searchField: String?,
        authorityCodes: Set<AdminAuthorityCode>?,
        activityStatuses: Set<AccountActivityStatus>?,
        enableStatus: AccountEnableStatus?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
    ): Data<Page<Admin>>

    suspend fun create(
        email: String,
        phone: String,
        password: String,
        fullName: String,
        birthDate: LocalDate?,
        gender: Gender?,
        avatarId: String?,
        authorityCodes: Set<AuthorityCode>,
        successOnExists: ((AdminEntity) -> Boolean)? = null,
    ): Data<String>

    suspend fun updateById(
        adminId: String,
        updateAdminData: UpdateAdminData?
    ): Data<String>

    suspend fun getById(adminId: String): Data<Admin>

    suspend fun delete(
        adminId: String
    ): Data<Unit>

    suspend fun deleteList(
        ids: Set<String>
    ): Data<Unit>

    suspend fun addUserClaims(userId: String, claims: Map<String, Any>): Data<Unit>
}