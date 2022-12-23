package dev.december.jeterbackend.admin.features.terrain.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.terrain.domain.models.Terrain

interface TerrainService {
    suspend fun create(
        title: String,
        specialities: List<String>?,
        priority: Int,
        description: String,
        file: File?
    ): Data<String>

    suspend fun getAll(): Data<List<Terrain>>
    suspend fun get(id: String): Data<Terrain>
    suspend fun delete(id: String): Data<Unit>
    suspend fun update(
        id: String,
        title: String?,
//        specialistIds: Set<String>?,
        priority: Int?,
        description: String?,
        file: File?,
    ): Data<Unit>

//    suspend fun getSpecialityFromProfession(professionId: String): Data<List<SpecialityProfession>>
}