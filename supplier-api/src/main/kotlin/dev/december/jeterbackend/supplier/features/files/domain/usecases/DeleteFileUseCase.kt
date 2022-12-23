package dev.december.jeterbackend.supplier.features.files.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.files.domain.models.FileDirectory
import dev.december.jeterbackend.supplier.features.files.domain.services.FileService
import org.springframework.stereotype.Component

@Component
class DeleteFileUseCase(
    private val fileService: FileService
) : UseCase<DeleteFileParams, Unit> {
    override suspend fun invoke(params: DeleteFileParams): Data<Unit> {
        return fileService.deleteFile(params.id, params.directory, params.format)
    }
}


data class DeleteFileParams(
    val id: String,
    val directory: FileDirectory,
    val format: String,
)