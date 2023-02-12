package dev.december.jeterbackend.scheduler.features.files.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.files.domain.models.FileDirectory

interface FileService {
    suspend fun deleteFile(id: String, directory: FileDirectory, format: String) : Data<Unit>
}