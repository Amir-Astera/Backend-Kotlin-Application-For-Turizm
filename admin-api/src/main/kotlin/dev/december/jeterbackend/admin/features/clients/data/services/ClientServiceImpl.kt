package dev.december.jeterbackend.admin.features.clients.data.services

import dev.december.jeterbackend.admin.features.clients.domain.errors.ClientDeleteFailure
import dev.december.jeterbackend.admin.features.clients.domain.errors.ClientListDeleteFailure
import dev.december.jeterbackend.admin.features.clients.domain.errors.ClientListGetFailure
import dev.december.jeterbackend.admin.features.clients.domain.errors.ClientNotFoundFailure
import dev.december.jeterbackend.admin.features.clients.domain.services.ClientService
import dev.december.jeterbackend.admin.features.clients.presentation.dto.UpdateClientData
import dev.december.jeterbackend.admin.features.suppliers.domain.errors.SupplierEnableFailure
import dev.december.jeterbackend.admin.features.suppliers.domain.errors.SupplierGetFailure
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.OsType
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.authorities.data.entities.UserAuthorityRepository
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.clients.data.repositories.specifications.ClientSpecification
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.clients.domain.models.ClientSortField
import dev.december.jeterbackend.shared.features.clients.domain.models.ClientUser
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ClientServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val clientRepository: ClientRepository,
) : ClientService {

    override suspend fun getList(
        sortField: ClientSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        searchField: String?,
        activityStatuses: Set<AccountActivityStatus>?,
        osTypes: Set<OsType>?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
        enableStatus: AccountEnableStatus?
    ): Data<Page<Client>> {
        return try {
            withContext(dispatcher) {
                val sortParams = sortField.getSortFields(sortDirection)
                val pageable = PageRequest.of(page, size, sortParams)

                val specification =
                    Specification.where(ClientSpecification.hasEnableStatus(enableStatus))
                        .and(ClientSpecification.containsFullName(searchField))
                        .and(ClientSpecification.isGreaterThanCreatedAt(createdFrom))
                        .and(ClientSpecification.isLessThanCreatedAt(createdTo))
                        .and(ClientSpecification.isInActivityStatus(activityStatuses))
                        .and(ClientSpecification.isInOsType(osTypes))

                val clientEntities = clientRepository.findAll(specification, pageable)

                val clients = clientEntities.map { it.client() }
                Data.Success(clients)
            }
        } catch (e: Exception) {
            return Data.Error(ClientListGetFailure())
        }
    }

    override suspend fun get(clientId: String): Data<Client> {
        return try {
            withContext(dispatcher) {

                val clientEntity = clientRepository.findByIdOrNull(clientId)
                    ?: return@withContext Data.Error(ClientNotFoundFailure())

                Data.Success(clientEntity.client())
            }
        } catch (e: Exception) {
            println(e.message)
            Data.Error(SupplierGetFailure())
        }
    }

    override suspend fun disableById(clientId: String): Data<String> {
        return try {
            withContext(dispatcher) {

                val clientEntity = clientRepository.findByIdOrNull(clientId)
                    ?: return@withContext Data.Error(ClientNotFoundFailure())
                clientRepository.save(
                    clientEntity.copy(
                        enableStatus = AccountEnableStatus.DISABLED
                    )
                )
                Data.Success(clientEntity.fullName + " successfully disabled!!")
            }
        } catch (e: Exception) {
            println(e.message)
            Data.Error(ClientListDeleteFailure())
        }
    }

    override suspend fun disableList(ids: Set<String>): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val clientEntities = clientRepository.findAllById(ids)
                if (clientEntities.count() != ids.size) {
                    return@withContext Data.Error(ClientNotFoundFailure())
                }
                clientRepository.saveAll(
                    clientEntities.map {
                        it.copy(enableStatus = AccountEnableStatus.DISABLED)
                    }
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(ClientDeleteFailure())
        }
    }

    override suspend fun enable(id: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val clientEntity = clientRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(ClientNotFoundFailure())
                clientRepository.save(
                    clientEntity.copy(
                        enableStatus = AccountEnableStatus.ENABLED
                    )
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(SupplierEnableFailure())
        }
    }
}