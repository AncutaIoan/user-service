package user_service.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import reactor.core.publisher.Mono
import user_service.model.CreateUserRequest
import user_service.model.LoginRequest
import user_service.model.LoginResponse
import user_service.repository.UserRepository
import user_service.model.Result
import user_service.repository.entity.User

class UserServiceTest {

    private val userRepository: UserRepository = mock(UserRepository::class.java)
    private val userCreationValidationService: UserCreationValidationService = mock(UserCreationValidationService::class.java)
    private val userService = UserService(userRepository, userCreationValidationService)

    @Test
    fun createUser_whenValidationFails_returnInvalid() {
        val request = CreateUserRequest("test@example.com", "testuser", "ValidPass123!")

        whenever(userCreationValidationService.validate(request)).thenReturn(Mono.just(Result.INVALID("Invalid data")))

        val result = userService.createUser(request).block()

        assertThat(result).isEqualTo(Result.INVALID("Invalid data"))
    }

    @Test
    fun createUser_whenValidationSucceeds_saveUserAndReturnValid() {
        val request = CreateUserRequest("test@example.com", "testuser", "ValidPass123!")
        val user = request.toUser()

        whenever(userCreationValidationService.validate(request)).thenReturn(Mono.just(Result.VALID))
        whenever(userRepository.save(any())).thenReturn(Mono.just(user))

        val result = userService.createUser(request).block()

        assertThat(result).isEqualTo(Result.VALID)
    }

    @Test
    fun createUser_whenErrorOccurs_returnGenericError() {
        val request = CreateUserRequest("test@example.com", "testuser", "ValidPass123!")

        whenever(userCreationValidationService.validate(request)).thenReturn(Mono.just(Result.VALID))
        whenever(userRepository.save(any())).thenReturn(Mono.error(RuntimeException("DB error")))

        val result = userService.createUser(request).block()

        assertThat(result).isEqualTo(Result.INVALID("Error occurred, try again!"))
    }

    @Test
    fun loginBy_whenUserExists_returnLoginResponse() {
        val request = LoginRequest("test@example.com", "ValidPass123!")
        val user = User(null, "testuser", "test@example.com", "hashedPassword")

        whenever(userRepository.findByEmailAndPasswordHash(eq(request.email), any())).thenReturn(Mono.just(user))

        val response = userService.loginBy(request).block()

        assertThat(response?.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response?.body).isEqualTo(LoginResponse.fromUser(user))
    }

    @Test
    fun loginBy_whenUserNotFound_returnNotFound() {
        val request = LoginRequest("test@example.com", "InvalidPass")

        whenever(userRepository.findByEmailAndPasswordHash(eq(request.email), any())).thenReturn(Mono.empty())

        val response = userService.loginBy(request).block()

        assertThat(response?.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}
