package dev.december.jeterbackend.admin.features.terrain.presentation.rest

import dev.december.jeterbackend.admin.features.terrain.domain.usecases.*
import dev.december.jeterbackend.admin.features.terrain.presentation.dto.CreateTerrainData
import dev.december.jeterbackend.admin.features.terrain.presentation.dto.UpdateTerrainData
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI


@RestController
@RequestMapping("/api/professions")
@Tag(name = "profession", description = "The Profession API")
class ProfessionController (
    private val createTerrainUseCase: CreateTerrainUseCase,
    private val getTerrainListUseCase: GetTerrainListUseCase,
    private val getTerrainUseCase: GetTerrainUseCase,
    private val deleteTerrainUseCase: DeleteTerrainUseCase,
    private val updateTerrainUseCase: UpdateTerrainUseCase,
//    private val getSpecialityFromProfessionUseCase: GetSpecialityFromProfessionUseCase,
    ) {
    @SecurityRequirement(name = "security_auth")
    @PostMapping
    fun create(
        @RequestBody data: CreateTerrainData,
        @Parameter(hidden = true) request: ServerHttpRequest
    ): Mono<ResponseEntity<Any>> {
        return mono { createTerrainUseCase(data.convert(mapOf("specialities" to data.specialistIds))) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.created(URI.create("${request.uri}/${it.data}")).build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping
    fun getAll(): Mono<ResponseEntity<Any>>{
        return mono{ getTerrainListUseCase(Unit) }.map {
            when (it){
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping("{id}")
    fun get(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { getTerrainUseCase(GetTerrainParams(id)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name ="security_auth")
    @DeleteMapping("{id}")
    fun delete(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { deleteTerrainUseCase(DeleteTerrainParams(id)) }.map {
            when(it)  {
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
    @PutMapping("{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody data: UpdateTerrainData,
    ): Mono<ResponseEntity<Any>> {
        return mono { updateTerrainUseCase(data.convert(mapOf("id" to id))) }.map {
            when(it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

//    @SecurityRequirement(name = "security_auth")
//    @GetMapping("{id}/specialities")
//    fun getSpecialities(
//        @PathVariable id: String,
//    ): Mono<ResponseEntity<Any>> {
//        return mono { getSpecialityFromProfessionUseCase(GetSpecialitiesParams(id)) }.map {
//            when(it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().body(it.data)
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure)
//                }
//            }
//        }
//    }

}