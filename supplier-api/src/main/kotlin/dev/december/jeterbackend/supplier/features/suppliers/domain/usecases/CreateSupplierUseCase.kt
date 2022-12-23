package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.domain.models.*
import dev.december.jeterbackend.shared.features.tours.domain.models.SupplierPrice
import org.springframework.stereotype.Component

@Component
class CreateSupplierUseCase(
    private val supplierService: SupplierService,
) : UseCase<CreateSupplierParams, String> {
    override suspend fun invoke(params: CreateSupplierParams): Data<String> {
        return supplierService.createSupplier(
            params.email,
            params.phone,
            params.password,
            params.generalInfo,
            params.education,
            params.certificate,
            params.socialMedia,
            params.bankAccount,
            params.avatar,
            params.schedule,
            params.price,
            params.files,
            params.registrationToken
            )
//            .fold { supplierService.addUserClaims(it, mapOf("" to )).fold { userId ->
//            Data.Success(userId)
//        } }
        }
    }


data class CreateSupplierParams(
    val phone: String,
    val email: String,
    val password: String,
    val generalInfo: SupplierGeneralInfo,
    val education: List<SupplierEducation>,
    val certificate: List<SupplierCertificate>,
    val bankAccount: SupplierBankAccount?,
    val socialMedia: SupplierSocialMedia?,
    val avatar: File?,
    val schedule: SupplierSchedule?,
    val price: SupplierPrice?,
    val files: List<File>?,
    val registrationToken: String?
)