package dev.december.jeterbackend.admin.features.admin.domain.usecases

import dev.december.jeterbackend.admin.features.admin.domain.services.AdminService
import dev.december.jeterbackend.shared.core.domain.model.UserGender
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminAuthorityCode
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CreateAdminUseCase(
    private val adminService: AdminService,
) : UseCase<CreateAdminParams, String> {
    override suspend fun invoke(params: CreateAdminParams): Data<String> {
        return adminService.create(params.fullName, params.email, params.phone, params.password, params.birthDate, params.gender, params.avatarId)
//            .fold { adminId ->
//                adminService.addUserClaims(adminId, mapOf("adminId" to adminId, "adminAuthority" to params.authorityCode.name)).fold {
//                    Data.Success(adminId)
//                }
//            }
        }
    }


data class CreateAdminParams(
    val fullName: String,
    val gender: UserGender? = UserGender.UNKNOWN,
    val email: String,
    val phone: String,
    val password: String,
    val birthDate: LocalDate?,
    val avatarId: String?,
    val authorityCode: AdminAuthorityCode
)