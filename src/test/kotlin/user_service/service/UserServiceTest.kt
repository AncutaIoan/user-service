package user_service.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import user_service.model.UserDto
import user_service.repository.UserRepository
import user_service.repository.entity.User

class UserServiceTest {
    private val userRepository = mock(UserRepository::class.java)
    private val userService = UserService(userRepository)

    @Test
    fun getUserById_whenFound_retrievesUserDto() {
        val user = User(null, "testuser", "test@example.com", "$2a$10abcd1234hashvalue")

        whenever(userRepository.findById(1L)).thenReturn(Mono.just(user))

        val userDto = userService.getUserById(1L).block()

        assertThat(userDto).isEqualTo(UserDto.from(user))
    }
}