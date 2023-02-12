package dev.december.jeterbackend.scheduler.features.client.data.services

import dev.december.jeterbackend.scheduler.core.config.properties.TaskProperties
import dev.december.jeterbackend.scheduler.core.config.security.firebase.FirebaseConfig
import dev.december.jeterbackend.scheduler.features.client.domain.services.ClientService
import dev.december.jeterbackend.scheduler.features.supplier.domain.errors.DeleteSupplierFailure
import dev.december.jeterbackend.scheduler.features.files.domain.services.FileService
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class ClientServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val clientRepository: ClientRepository,
    private val fileService: FileService,
    private val firebaseConfig: FirebaseConfig,
    private val taskProperties: TaskProperties,
    private val logger: Logger,
) : ClientService {
    override suspend fun delete(): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val minusDays = taskProperties.clientTask.minusDays
                val deleteDay = LocalDateTime.now().minusDays(minusDays.toLong())
                val from = deleteDay.with(LocalTime.MIN)
                val to = deleteDay.with(LocalTime.MAX)
                val clients = clientRepository.findAllByEnableStatusAndDeletedAtBetween(AccountEnableStatus.DISABLED, from, to)
                clients.forEach { client ->
                    firebaseConfig.firebaseAuth(PlatformRole.CLIENT).deleteUser(client.id)
                    if (client.file != null) {
                        fileService.deleteFile(
                            client.file!!.id,
                            client.file!!.directory,
                            client.file!!.format
                        )
                    }
                }
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(DeleteSupplierFailure())
        }
    }

}