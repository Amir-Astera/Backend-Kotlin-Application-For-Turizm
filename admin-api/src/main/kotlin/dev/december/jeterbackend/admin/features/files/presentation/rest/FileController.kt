package dev.december.jeterbackend.admin.features.files.presentation.rest

import dev.december.jeterbackend.admin.features.files.presentation.dto.UpdateFileData
import dev.december.jeterbackend.admin.features.files.domain.usecases.*
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.files.domain.models.FileDirectory
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/files")
@Tag(name = "files", description = "The Files API")
class FileController(
    private val uploadFileUseCase: UploadFileUseCase,
    private val getFileUseCase: GetFileUseCase,
    private val updateFileUseCase: UpdateFileUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
) {
    @SecurityRequirement(name = "security_auth")
    @PostMapping("/{directory}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestHeader("Content-Length") contentLength: Long,
        @PathVariable directory: FileDirectory,
        @RequestParam(required = false) priority: Int?,
        @RequestPart part: FilePart,
        @Parameter(hidden = true) request: ServerHttpRequest,
    ): Mono<ResponseEntity<Any>> {
        return mono { uploadFileUseCase(UploadFileParams(directory, part, priority, contentLength)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping(
        "/{directory}/{id}.{format}",
        produces = [
            MediaType.ALL_VALUE
        ]
    )
    fun getFile(
        @PathVariable directory: FileDirectory,
        @PathVariable id: String,
        @PathVariable format: String,
    ): Mono<ResponseEntity<Any>> {
        return mono { getFileUseCase(GetFileParams(directory, id, format)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).contentType(MediaType.APPLICATION_JSON).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateFile(
        @PathVariable id: String,
        @RequestBody data: UpdateFileData,
    ): Mono<ResponseEntity<Any>> {
        return mono { updateFileUseCase(data.convert(mapOf("id" to id))) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @DeleteMapping("/{directory}/{id}.{format}", produces = [MediaType.ALL_VALUE])
    fun deleteFile(
        @PathVariable id: String,
        @PathVariable directory: FileDirectory,
        @PathVariable format: String,
    ): Mono<ResponseEntity<Any>> {
        return mono { deleteFileUseCase(DeleteFileParams(id, directory, format)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }
}


