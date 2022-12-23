package dev.december.jeterbackend.supplier.features.files.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.files.domain.models.FileDirectory
import dev.december.jeterbackend.supplier.features.files.domain.services.FileService
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component

@Component
class UpdateFileUseCase (
    private val fileService: FileService
) : UseCase<UpdateFileParams, File> {
    override suspend fun invoke(params: UpdateFileParams): Data<File> {
        return fileService.updateFile(params.id, params.directory, params.part, params.priority)
    }
}

data class UpdateFileParams(
    val id : String,
    val directory: FileDirectory?,
    val priority : Int?,
    val part: FilePart?,
)