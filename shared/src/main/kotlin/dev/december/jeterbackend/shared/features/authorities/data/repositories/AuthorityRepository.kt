package dev.december.jeterbackend.shared.features.authorities.data.repositories

import dev.december.jeterbackend.shared.features.authorities.data.entities.AuthorityEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepository : PagingAndSortingRepository<AuthorityEntity, String> {
    fun findAllByCodeIn(codes: Set<String>): Set<AuthorityEntity>
}