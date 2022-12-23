package dev.december.jeterbackend.shared.features.terrain.data.repositories


import dev.december.jeterbackend.shared.features.terrain.data.entities.TerrainEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TerrainRepository : CrudRepository<TerrainEntity, String> {
    fun findAllByOrderByPriorityAsc(): List<TerrainEntity>
    override fun findById(id: String): Optional<TerrainEntity>
}