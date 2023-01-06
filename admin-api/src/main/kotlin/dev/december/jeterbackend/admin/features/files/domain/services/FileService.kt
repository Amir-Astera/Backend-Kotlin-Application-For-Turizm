package dev.december.jeterbackend.admin.features.files.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.files.domain.models.FileDirectory
import org.springframework.core.io.Resource
import org.springframework.http.codec.multipart.FilePart
import java.nio.file.Path

interface FileService {
    suspend fun storeFile(directory: FileDirectory, part: FilePart, priority: Int, contentLength: Long): Data<File>
    suspend fun getFile(directory: FileDirectory, id: String, format: String): Data<Resource>
    suspend fun updateFile(id: String, directory: FileDirectory?, part: FilePart?, priority: Int?): Data<File>
    suspend fun deleteFile(id: String, directory: FileDirectory, format: String) : Data<Unit>
}