package user_service.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import user_service.model.UserDto
import user_service.repository.UserRepository

@Service
class UserService(private val userRepository: UserRepository) {
    fun getUserById(userId: Long): Mono<UserDto> = userRepository.findById(userId).map { user -> UserDto.from(user) }
}