package dev.december.jeterbackend.client.features.clients.domain.usecases

import dev.december.jeterbackend.client.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.model.UserGender
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.files.domain.models.File
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CreateClientUseCase(
    private val clientService: ClientService
) : UseCase<CreateClientParams, String> {
    override suspend fun invoke(params: CreateClientParams): Data<String> {
        return clientService.create(
                params.email,
                params.phone,
                params.password,
                params.fullName,
                params.birthDate,
                params.gender,
                params.avatar,
                params.registrationToken
            )
//            .fold { clientService.addUserClaims(it, mapOf("" to )) }.fold { userId ->
//                Data.Success(userId)
//        }
        }
    }


data class CreateClientParams(
    val phone: String,
    val email: String,
    val password: String,
    val fullName: String,
    val birthDate: LocalDate? = null,
    val gender: UserGender? = null,
    val avatar: File? = null,
    val registrationToken: String? = null,
)
