package dev.december.jeterbackend.shared.features.tours.data.repositories

import dev.december.jeterbackend.shared.features.tours.data.entities.ScheduleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ScheduleRepository : JpaRepository<ScheduleEntity, String>{
    fun findBySupplierId(supplierId: String): ScheduleEntity?
}