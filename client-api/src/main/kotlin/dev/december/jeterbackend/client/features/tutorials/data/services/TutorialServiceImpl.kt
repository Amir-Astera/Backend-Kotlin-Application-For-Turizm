package dev.december.jeterbackend.client.features.tutorials.data.services

import dev.december.jeterbackend.client.features.tutorials.domain.error.TutorialGetFailure
import dev.december.jeterbackend.client.features.tutorials.domain.error.TutorialListGetFailure
import dev.december.jeterbackend.shared.features.tutorials.data.repositories.TutorialRepository
import dev.december.jeterbackend.shared.features.tutorials.domain.models.Tutorial
import dev.december.jeterbackend.client.features.tutorials.domain.error.TutorialNotFoundFailure
import dev.december.jeterbackend.client.features.tutorials.domain.services.TutorialService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.domain.models.File
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TutorialServiceImpl(
    private val tutorialRepository: TutorialRepository,
    private val dispatcher: CoroutineDispatcher,
) : TutorialService {

    override suspend fun get(id: String): Data<Tutorial> {
        return try {
            withContext(dispatcher) {
                val tutorial = tutorialRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(TutorialNotFoundFailure())
                Data.Success(tutorial.convert())
            }
        } catch (e: Exception) {
            Data.Error(TutorialGetFailure())
        }
    }

    override suspend fun getAll(authority: PlatformRole): Data<List<Tutorial>> {
        return try {
            withContext(dispatcher) {
                val entities = tutorialRepository.findAllByAuthorityOrderByPriorityAsc(authority)
                val tutorials: List<Tutorial> = entities.map {
                    it.convert(mapOf("file" to it.file?.convert<FileEntity, File>()))
                }
                Data.Success(tutorials)
            }
        } catch (e: Exception) {
            Data.Error(TutorialListGetFailure())
        }
    }
}