package user_service.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import user_service.model.CreateUserRequest
import user_service.repository.UserRepository
import user_service.model.Result

class UserCreationValidationServiceTest {

    private val userRepository: UserRepository = mock(UserRepository::class.java)
    private val passwordValidationService: PasswordValidationService = mock(PasswordValidationService::class.java)
    private val userCreationValidationService = UserCreationValidationService(userRepository, passwordValidationService)

    @Test
    fun validate_whenEmailOrUsernameExists_raiseError() {
        val request = CreateUserRequest("test@example.com", "testuser", "ValidPass123!")

        whenever(userRepository.existsByEmailOrUsername(request.email, request.username)).thenReturn(Mono.just(true))
        whenever(passwordValidationService.validate(request.password)).thenReturn(Mono.just(true))

        val result = userCreationValidationService.validate(request).block()

        assertThat(result).isEqualTo(Result.INVALID("Email or Username is already taken."))
    }

    @Test
    fun validate_whenPasswordIsInvalid_raiseError() {
        val request = CreateUserRequest("test@example.com", "testuser", "weak")

        whenever(userRepository.existsByEmailOrUsername(request.email, request.username)).thenReturn(Mono.just(false))
        whenever(passwordValidationService.validate(request.password)).thenReturn(Mono.just(false))

        val result = userCreationValidationService.validate(request).block()

        assertThat(result).isEqualTo(Result.INVALID("Password is invalid. Please follow the password requirements."))
    }

    @Test
    fun validate_whenEmailUsernameAndPasswordAreValid_returnValid() {
        val request = CreateUserRequest("test@example.com", "testuser", "ValidPass123!")

        whenever(userRepository.existsByEmailOrUsername(request.email, request.username)).thenReturn(Mono.just(false))
        whenever(passwordValidationService.validate(request.password)).thenReturn(Mono.just(true))

        val result = userCreationValidationService.validate(request).block()

        assertThat(result).isEqualTo(Result.VALID)
    }
}
