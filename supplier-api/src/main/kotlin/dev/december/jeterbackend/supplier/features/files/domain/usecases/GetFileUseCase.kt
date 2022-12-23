package dev.december.jeterbackend.supplier.features.files.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.files.domain.models.FileDirectory
import dev.december.jeterbackend.supplier.features.files.domain.services.FileService
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class GetFileUseCase(
    private val fileService: FileService
) : UseCase<GetFileParams, Resource> {
    override suspend fun invoke(params: GetFileParams): Data<Resource> {
        return fileService.getFile(params.directory, params.id, params.format )
    }
}

data class GetFileParams(
    val directory: FileDirectory,
    val id: String,
    val format: String,
)