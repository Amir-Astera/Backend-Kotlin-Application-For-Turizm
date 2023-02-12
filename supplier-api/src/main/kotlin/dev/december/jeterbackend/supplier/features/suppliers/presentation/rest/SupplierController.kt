package dev.december.jeterbackend.supplier.features.suppliers.presentation.rest

import dev.december.jeterbackend.supplier.features.suppliers.presentation.dto.CreateSupplierData
import dev.december.jeterbackend.supplier.features.suppliers.presentation.dto.UpdateSupplierCalendar
import dev.december.jeterbackend.supplier.features.suppliers.presentation.dto.UpdateSupplierDescriptionDto
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.Language
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.suppliers.domain.models.*
import dev.december.jeterbackend.shared.features.tours.domain.models.SupplierPrice
import dev.december.jeterbackend.supplier.core.config.security.SessionUser
import dev.december.jeterbackend.supplier.features.suppliers.domain.usecases.*
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI
import java.time.LocalDate


@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "suppliers", description = "The Suppliers API")
class SupplierController(
    private val createSupplierUseCase: CreateSupplierUseCase,
    private val getSupplierUseCase: GetSupplierUseCase,
    private val patchScheduleUseCase: PatchScheduleUseCase,
    private val addEducationUseCase: AddEducationUseCase,
    private val deleteEducationUseCase: DeleteEducationUseCase,
    private val updateEducationUseCase: UpdateEducationUseCase,
    private val addCertificateUseCase: AddCertificateUseCase,
    private val deleteCertificateUseCase: DeleteCertificateUseCase,
    private val updateCertificateUseCase: UpdateCertificateUseCase,
    private val patchGeneralInfoUseCase: PatchGeneralInfoUseCase,
    private val patchPriceUseCase: PatchPriceUseCase,
    private val patchSocialMediaUseCase: PatchSocialMediaUseCase,
    private val deleteSupplierByUserIdUseCase: DeleteSupplierByUserIdUseCase,
    private val getSupplierCalendarUseCase: GetSupplierCalendarUseCase,
    private val updateSupplierCalendarUseCase: UpdateSupplierCalendarUseCase,
    private val getSupplierByPhoneNumber: GetSupplierByPhoneNumberUseCase,
    private val patchBankAccountUseCase: PatchBankAccountUseCase,
    private val toggleNotificationUseCase: ToggleNotificationUseCase,
    private val updateVideoAppointmentPriceUseCase: UpdateVideoAppointmentPriceUseCase,
    private val updateAudioAppointmentPriceUseCase: UpdateAudioAppointmentPriceUseCase,
    private val updateSupplierDescriptionUseCase: UpdateSupplierDescriptionUseCase,
    private val updateSupplierActivityStatusUseCase: UpdateSupplierActivityStatusUseCase,
    private val updateRegistrationTokenUseCase: UpdateRegistrationTokenUseCase,
    private val restoreSupplierUseCase: RestoreSupplierUseCase,
    private val updateExperienceUseCase: UpdateSupplierExperienceUseCase,
    private val updateEmailUseCase: UpdateEmailUseCase,
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
) {


    @SecurityRequirement(name = "security_auth")
    @PostMapping("/verify-email")
    fun verifyEmail(
        @Parameter(hidden = true) authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id
        return mono { verifyEmailUseCase(VerifyEmailParams(userId)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/email")
    fun putEmail(
        @RequestParam email: String,
        @Parameter(hidden = true) authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        return mono { updateEmailUseCase(UpdateEmailParams(user.id, email)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @PostMapping(value = ["/restore"])
    fun restore(
        @Parameter(hidden = true) request: ServerHttpRequest,
        @Parameter(hidden = true) authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        return mono { restoreSupplierUseCase(RestoreSupplierParams(user.id, user.signInProvider)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @DeleteMapping(value = ["/current"])
    fun deleteCurrent(
        @Parameter(hidden = true) request: ServerHttpRequest,
        @Parameter(hidden = true) authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        return mono { deleteSupplierByUserIdUseCase(DeleteSupplierByUserIdParams(user.id, user.signInProvider)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @PostMapping
    fun create(
        @RequestBody supplier: CreateSupplierData,
        @Parameter(hidden = true) request: ServerHttpRequest,
    ): Mono<ResponseEntity<Any>> {

        return mono { createSupplierUseCase(supplier.convert()) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.created(URI.create("${request.uri}/${it.data}")).build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PatchMapping("/schedule")
    fun updateSchedule(
        @RequestBody schedule: SupplierSchedule,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { patchScheduleUseCase(PatchScheduleParams(userId = userId, schedule = schedule)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PostMapping("/education")
    fun postEducation(
        @RequestBody education: SupplierEducation,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { addEducationUseCase(AddEducationParams(userId, education)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @DeleteMapping("/education/{id}")
    fun deleteEducation(
        @PathVariable id: String,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { deleteEducationUseCase(DeleteEducationParams(userId, id)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/education/{id}")
    fun updateEducation(
        @PathVariable id: String,
        @RequestBody data: SupplierEducationUpdate,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { updateEducationUseCase(UpdateEducationParams(userId, id, data)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PostMapping("/certificate")
    fun postCertificate(
        @RequestBody certificate: SupplierCertificate,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { addCertificateUseCase(AddCertificateParams(userId, certificate)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @DeleteMapping("/certificate/{id}")
    fun deleteCertificate(
        @PathVariable id: String,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { deleteCertificateUseCase(DeleteCertificateParams(userId, id)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    //
    @SecurityRequirement(name = "security_auth")
    @PutMapping("/certificate/{id}")
    fun updateCertifficate(
        @PathVariable id: String,
        @RequestBody data: SupplierCertificateUpdate,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { updateCertificateUseCase(UpdateCertificateParams(userId, id, data)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PatchMapping("/generalinfo")
    fun updateGeneralInfo(
        @RequestBody generalInfo: SupplierGeneralInfoUpdate,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { patchGeneralInfoUseCase(PatchGeneralInfoParams(userId, generalInfo)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PatchMapping("/price")
    fun updatePrice(
        @RequestBody price: SupplierPrice,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { patchPriceUseCase(PatchPriceParams(userId, price)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PatchMapping("/socialMedia")
    fun updateSocialMedia(
        @RequestBody price: SupplierSocialMedia,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { patchSocialMediaUseCase(PatchSocialMediaParams(userId, price)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping("/current")
    fun get(
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { getSupplierUseCase(GetSupplierParams(userId)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @GetMapping("/checkExistence")
    fun getByPhoneNumber(
        @RequestParam(required = false)
        phone: String,
    ): Mono<ResponseEntity<Any>> {

        return mono { getSupplierByPhoneNumber(GetSupplierByPhoneParams(phone)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PatchMapping("/bankAccount")
    fun updateBankAccount(
        @RequestBody bankAccount: SupplierBankAccount,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { patchBankAccountUseCase(PatchBankAccountParams(userId, bankAccount)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/toggle-notification")
    fun toggleNotification(
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
        @RequestParam toggleStatus: Boolean,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { toggleNotificationUseCase(ToggleNotificationParams(userId, toggleStatus)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }


    @SecurityRequirement(name = "security_auth")
    @GetMapping("/calendar")
    fun getCalendar(
        @RequestParam(required = true)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        firstDayOfMonth: LocalDate,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono {
            getSupplierCalendarUseCase(
                GetSupplierCalendarParams(userId, firstDayOfMonth)
            )
        }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PatchMapping("/calendar")
    fun updateCalendar(
        @RequestBody supplierCalendar: UpdateSupplierCalendar,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { updateSupplierCalendarUseCase(UpdateSupplierCalendarParams(userId, supplierCalendar)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/video-price")
    fun updateVideoAppointmentPrice(
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
        @RequestParam price: Int?,
    ): Mono<ResponseEntity<Any>> {

        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono {
            updateVideoAppointmentPriceUseCase(
                UpdateVideoAppointmentPriceParams(
                    userId, price
                )
            )
        }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/audio-price")
    fun updateAudioAppointmentPrice(
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
        @RequestParam price: Int?,
    ): Mono<ResponseEntity<Any>> {

        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono {
            updateAudioAppointmentPriceUseCase(
                UpdateAudioAppointmentPriceParams(
                    userId, price
                )
            )
        }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/registration-token")
    fun updateRegistrationToken(
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
        @RequestParam registrationToken: String,
    ): Mono<ResponseEntity<Any>> {

        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono {
            updateRegistrationTokenUseCase(
                UpdateRegistrationTokenParams(
                    userId, registrationToken
                )
            )
        }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/description")
    fun updateDescription(
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
        @RequestBody dto: UpdateSupplierDescriptionDto,
    ): Mono<ResponseEntity<Any>> {

        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono {
            updateSupplierDescriptionUseCase(
                UpdateSupplierDescriptionParams(
                    userId, dto.description
                )
            )
        }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/activity-status")
    fun updateActivityStatus(
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
        @RequestParam activityStatus: AccountActivityStatus,
        @RequestParam
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        firstDayOfMonth: LocalDate,
    ): Mono<ResponseEntity<Any>> {

        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono {
            updateSupplierActivityStatusUseCase(
                UpdateSupplierActivityStatusParams(
                    userId,
                    activityStatus,
                    firstDayOfMonth,
                )
            )
        }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/experience")
    fun updateExperience(
        @RequestParam(required = true)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        experience: LocalDate?,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { updateExperienceUseCase(UpdateSupplierExperienceParams(userId, experience)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/language")
    fun updateLanguage(
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
        @RequestParam language: Language
    ): Mono<ResponseEntity<Any>> {

        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { updateLanguageUseCase(
            UpdateLanguageUseCaseParams(
                userId, language
            )
        ) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

}