package dev.december.jeterbackend.admin.features.clients.domain.usecases

import dev.december.jeterbackend.admin.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.model.UserGender
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component
import java.time.LocalDate

//@Component
//class CreateClientUseCase(
//    private val clientService: ClientService
//) : UseCase<CreateClientParams, String> {
//    override suspend fun invoke(params: CreateClientParams): Data<String> {
//        return clientService.create(
//            params.userId,
//            params.login,
//            params.fullName,
//            params.birthDate,
//            params.gender,
//            params.avatarId,
//            params.registrationToken
//        )
//    }
//}
//
//data class CreateClientParams(
//    val userId: String,
//    val login: String,
//    val fullName: String,
//    val birthDate: LocalDate?,
//    val gender: UserGender?,
//    val avatarId: String?,
//    val registrationToken: String
//)