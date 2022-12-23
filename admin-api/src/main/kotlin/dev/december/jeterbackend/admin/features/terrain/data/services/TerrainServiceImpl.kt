package dev.december.jeterbackend.admin.features.terrain.data.services

import dev.december.jeterbackend.admin.features.files.domain.error.FileNotFoundFailure
import dev.december.jeterbackend.admin.features.terrain.domain.errors.*
import dev.december.jeterbackend.admin.features.terrain.domain.services.TerrainService
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.terrain.data.entities.TerrainEntity
import dev.december.jeterbackend.shared.features.terrain.data.repositories.TerrainRepository
import dev.december.jeterbackend.shared.features.terrain.domain.models.Terrain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TerrainServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val fileRepository: FileRepository,
    private val terrainRepository: TerrainRepository
) : TerrainService {
    override suspend fun create(
        title: String,
        specialities: List<String>?,
        priority: Int,
        description: String,
        file: File?
    ): Data<String> {
        return try {
            withContext(dispatcher) {
                val fileEntity = file?.let { fileRepository.findByIdOrNull(it.id) }
//                val specialityEntities =
//                    specialities?.let { specialityRepository.findAllById(it) }
//                specialityEntities?.forEach {
//                    if (it.profession != null) return@withContext Data.Error(SpecialityHasProfessionFailure())
//                }
                val professionEntity = terrainRepository.save(
                    TerrainEntity(
                        title = title,
//                        specialities = specialityEntities?.toSet() ?: emptySet(),
                        description = description,
                        priority = priority,
                        file = fileEntity,
                    )
                )
                Data.Success(professionEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(TerrainCreateFailure())
        }
    }

    override suspend fun getAll(): Data<List<Terrain>> {
        return try {
            withContext(dispatcher) {
                val entities = terrainRepository.findAllByOrderByPriorityAsc()
                val professions: List<Terrain> = entities.map {
                    it.convert(
                        mapOf(
                            "file" to it.file?.convert<FileEntity, File>(),
                        )
                    )
                }
                Data.Success(professions)
            }
        } catch (e: Exception) {
            Data.Error(TerrainGetListFailure())
        }
    }

    override suspend fun get(id: String): Data<Terrain> {
        return try {
            withContext(dispatcher) {
                val entity =
                    terrainRepository.findByIdOrNull(id) ?: return@withContext Data.Error(TerrainGetFailure())
                val professions: Terrain = entity.convert(
                    mapOf(
                        "file" to entity.file?.convert<FileEntity, File>(),
                    )
                )
                Data.Success(professions)
            }
        } catch (e: Exception) {
            Data.Error(TerrainGetFailure())
        }
    }

    override suspend fun delete(id: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val entity =
                    terrainRepository.findByIdOrNull(id) ?: return@withContext Data.Error(TerrainGetFailure())
                terrainRepository.deleteById(entity.id)
                entity.file?.let { fileRepository.delete(it) }
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(TerrainDeleteFailure())
        }
    }


    override suspend fun update(id: String, title: String?, priority: Int?, description: String?, file: File?): Data<Unit> {//, specialistIds: Set<String>?
        return try {
            withContext(dispatcher) {
                val oldEntity = terrainRepository.findByIdOrNull(id) ?: return@withContext Data.Error(
                    TerrainNotFoundFailure()
                )
                val newFileEntity = if (file != null) {
                    fileRepository.findByIdOrNull(file.id) ?: return@withContext Data.Error(FileNotFoundFailure())
                } else oldEntity.file

//                val specialityEntities =
//                    specialistIds?.let { specialityRepository.findAllById(it) }
//                specialityEntities?.forEach {
//                    if (it.profession != null) return@withContext Data.Error(SpecialityHasProfessionFailure())
//                }

                val newEntity = oldEntity.copy(
                    title = title ?: oldEntity.title,
                    priority = priority ?: oldEntity.priority,
                    description = description ?: oldEntity.description,
                    updatedAt = LocalDateTime.now(),
                    file = newFileEntity ?: oldEntity.file,
//                    specialities = specialityEntities?.toSet() ?: oldEntity.specialities
                )
                terrainRepository.save(newEntity)
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(TerrainUpdateFailure())
        }
    }

//    override suspend fun getSpecialityFromProfession(professionId: String): Data<List<SpecialityProfession>> {
//        return try {
//            withContext(dispatcher) {
//                val professionEntity = professionRepository.findByIdOrNull(professionId) ?:
//                return@withContext Data.Error(ProfessionNotFoundFailure())
//                val specialityEntities = specialityRepository.findAllByProfessionId(professionId)
//
//                val specialities: List<SpecialityProfession> = specialityEntities.map {
//                    it.convert(
//                        mapOf(
//                            "file" to it.file?.convert<FileEntity, File>(),
//                        )
//                    )
//                }
//
//                Data.Success(specialities)
//            }
//        } catch (e: Exception) {
//            Data.Error(SpecialityDeleteFailure())
//        }
//    }
}