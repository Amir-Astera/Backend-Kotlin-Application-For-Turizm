package dev.december.jeterbackend.shared.features.orders.data.repositories

import dev.december.jeterbackend.shared.features.orders.data.entities.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface OrderRepository: JpaRepository<OrderEntity, String>, JpaSpecificationExecutor<OrderEntity> {
}