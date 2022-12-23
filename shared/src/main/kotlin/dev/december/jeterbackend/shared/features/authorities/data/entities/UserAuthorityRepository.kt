package dev.december.jeterbackend.shared.features.authorities.data.entities

import dev.december.jeterbackend.shared.features.authorities.data.entities.UserAuthorityEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAuthorityRepository : CrudRepository<UserAuthorityEntity, String> {}