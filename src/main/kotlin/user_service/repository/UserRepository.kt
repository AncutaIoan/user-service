package user_service.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import user_service.repository.entity.User

@Repository
interface UserRepository: R2dbcRepository<User, Long>