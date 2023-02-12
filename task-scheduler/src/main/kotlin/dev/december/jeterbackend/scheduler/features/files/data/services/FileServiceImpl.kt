package dev.december.jeterbackend.scheduler.features.files.data.services

import dev.december.jeterbackend.scheduler.features.files.domain.error.FileDeleteFailure
import dev.december.jeterbackend.scheduler.features.files.domain.error.FileNotFoundFailure
import dev.december.jeterbackend.scheduler.features.files.domain.services.FileService
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import dev.december.jeterbackend.shared.features.files.domain.models.FileDirectory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

@Service
internal class FileServiceImpl(
    private val location: Path,
    private val dispatcher: CoroutineDispatcher,
    private val fileRepository: FileRepository,
) : FileService {

    override suspend fun deleteFile(id: String, directory: FileDirectory, format: String): Data<Unit> {
        val cleanPath: String = StringUtils.cleanPath("${directory.name.lowercase()}/$id.$format")
        val targetLocation = location.resolve(cleanPath)
        return try{
            withContext(dispatcher){
                if (targetLocation.exists() && !targetLocation.isDirectory()) {
                    Files.deleteIfExists(targetLocation)
                    fileRepository.deleteById(id)
                    Data.Success(Unit)
                }
                else{
                    Data.Error(FileNotFoundFailure())
                }
            }
        } catch (e: EmptyResultDataAccessException){
            Data.Error(FileNotFoundFailure())
        } catch (e: Exception){
            Data.Error(FileDeleteFailure())
        }
    }
}