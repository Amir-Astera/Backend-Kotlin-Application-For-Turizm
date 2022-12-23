package dev.december.jeterbackend.client.features.clients.domain.usecases

import dev.december.jeterbackend.client.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class UpdateRegistrationTokenUseCase (
    private val clientService: ClientService
) : UseCase<UpdateRegistrationTokenParams, Unit> {
    override suspend fun invoke(params: UpdateRegistrationTokenParams): Data<Unit> {
        return clientService.updateRegistrationToken(
            params.userId,
            params.registrationToken
        )
    }
}

data class UpdateRegistrationTokenParams(
    val userId: String,
    val registrationToken: String,
)