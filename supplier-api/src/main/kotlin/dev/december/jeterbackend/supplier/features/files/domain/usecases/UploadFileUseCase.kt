package dev.december.jeterbackend.supplier.features.files.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.files.domain.models.FileDirectory
import dev.december.jeterbackend.supplier.features.files.domain.services.FileService
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component

@Component
class UploadFileUseCase(
    private val fileService: FileService
) : UseCase<UploadFileParams, File> {
    override suspend fun invoke(params: UploadFileParams): Data<File> {
        return fileService.storeFile(params.directory, params.part, params.priority ?: 0, params.contentLength)
    }
}

data class UploadFileParams(
    val directory: FileDirectory,
    val part: FilePart,
    val priority: Int?,
    val contentLength: Long
)