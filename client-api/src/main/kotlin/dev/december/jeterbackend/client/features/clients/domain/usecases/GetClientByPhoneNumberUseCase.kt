package dev.december.jeterbackend.client.features.clients.domain.usecases

import dev.december.jeterbackend.client.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class GetClientByPhoneNumberUseCase (
    private val clientService: ClientService
) : UseCase<GetClientByPhoneParams, Boolean> {
    override suspend fun invoke(params: GetClientByPhoneParams): Data<Boolean> {
        return clientService.getByPhone(params.phone)
    }
}

data class GetClientByPhoneParams(
    val phone: String
)