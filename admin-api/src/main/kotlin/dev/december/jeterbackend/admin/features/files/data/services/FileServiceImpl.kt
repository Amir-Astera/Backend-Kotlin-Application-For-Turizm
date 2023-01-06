package dev.december.jeterbackend.admin.features.files.data.services

import dev.december.jeterbackend.admin.features.files.domain.error.FileDeleteFailure
import dev.december.jeterbackend.admin.features.files.domain.error.FileNotFoundFailure
import dev.december.jeterbackend.admin.features.files.domain.error.FileStoreFailure
import dev.december.jeterbackend.admin.features.files.domain.error.FileUpdateFailure
import dev.december.jeterbackend.admin.features.files.domain.services.FileService
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.files.domain.models.FileDirectory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.withContext
import org.apache.tika.mime.MimeTypes
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

@Service
internal class FileServiceImpl(
    private val location: Path,
    private val dispatcher: CoroutineDispatcher,
    private val fileRepository: FileRepository,
) : FileService {

    override suspend fun storeFile(directory: FileDirectory, part: FilePart, priority: Int, contentLength: Long): Data<File> {
        val allTypes = MimeTypes.getDefaultMimeTypes()
        val fileType = allTypes.forName(part.headers().contentType.toString())
        val originalFileName = part.filename()
        val format = fileType.extension.replace(".", "")
        val id = "${UUID.randomUUID()}"
        val fileName = "$id.$format"
        val cleanPath: String = StringUtils.cleanPath("${directory.name.lowercase()}/$fileName")
        return try {
            val targetLocation = location.resolve(cleanPath)
            withContext(Dispatchers.IO) {
                Files.createDirectories(targetLocation.parent)
                part.transferTo(targetLocation).awaitSingleOrNull()
            }
            withContext(dispatcher) {
                val fileEntity = fileRepository.save(
                    FileEntity(
                        id = id,
                        name = originalFileName,
                        size = contentLength,
                        format = format,
                        directory = directory,
                        url = "/api/files/${directory.name.lowercase()}/$fileName",
                        priority = priority
                    )
                )
                Data.Success(fileEntity.convert())
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Data.Error(FileStoreFailure())
        }
    }

    override suspend fun getFile(directory: FileDirectory, id: String, format: String): Data<Resource> {
        val cleanPath: String = StringUtils.cleanPath("${directory.name.lowercase()}/$id.$format")
        val targetLocation = location.resolve(cleanPath)
        return if (targetLocation.exists() && !targetLocation.isDirectory()) {
            Data.Success(FileSystemResource(targetLocation))
        } else {
            Data.Error(FileNotFoundFailure())
        }
    }

    override suspend fun updateFile(id: String, directory: FileDirectory?, part: FilePart?, priority: Int?): Data<File>{
        return try {
            if (directory == null && part == null){
                return Data.Error(FileNotFoundFailure())
            }
            withContext(dispatcher) {
                val oldEntity = fileRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(FileNotFoundFailure())
                val newEntity = oldEntity.copy(
                        directory = directory ?: oldEntity.directory,
                        priority = priority ?: oldEntity.priority, 
                )
                fileRepository.save(newEntity)
                Data.Success(newEntity.id.convert())
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Data.Error(FileUpdateFailure())
        }
    }

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