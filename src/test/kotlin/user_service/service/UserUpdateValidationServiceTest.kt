package user_service.service

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import user_service.config.AccountConfig
import user_service.model.Result
import user_service.repository.UserRepository

class UserUpdateValidationServiceTest {

    private val userRepository: UserRepository = mock(UserRepository::class.java)
    private val accountConfig: AccountConfig = mock(AccountConfig::class.java)
    private val userUpdateValidationService: UserUpdateValidationService = UserUpdateValidationService(userRepository, accountConfig)


    @Test
    fun validateUsername_whenUsernameIsNull_raiseError() {
        val username: String? = null

        assertThatCode { userUpdateValidationService.validateUsername(username).block() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Username cannot be empty or blank.")
    }

    @Test
    fun validateUsername_whenUsernameIsBlank_raiseError() {
        val username = "   "

        assertThatCode { userUpdateValidationService.validateUsername(username).block() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Username cannot be empty or blank.")
    }

    @Test
    fun validateUsername_whenUsernameIsValid_returnResult() {
        val username = "valid_username"

        whenever(accountConfig.usernameMinLength).thenReturn(3)
        whenever(accountConfig.usernameMaxLength).thenReturn(15)

        whenever(userRepository.existsByEmailOrUsername("", username)).thenReturn(Mono.just(false))

        val result = userUpdateValidationService.validateUsername(username).block()

        assertThat(result).isEqualTo(Result.VALID)
    }

    @Test
    fun validateUsername_whenUsernameTooShort_raiseError() {
        val username = "ab"

        whenever(accountConfig.usernameMinLength).thenReturn(3)
        whenever(accountConfig.usernameMaxLength).thenReturn(15)

        assertThatCode { userUpdateValidationService.validateUsername(username).block() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Username must be between 3 and 15 characters.")
    }

    @Test
    fun validateUsername_whenUsernameContainsInvalidChars_raiseError() {
        val username = "invalid@username"

        whenever(accountConfig.usernameMinLength).thenReturn(3)
        whenever(accountConfig.usernameMaxLength).thenReturn(25)

        assertThatCode { userUpdateValidationService.validateUsername(username).block() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Username can only contain letters, digits, and underscores.")
    }

    @Test
    fun validateEmail_whenEmailIsNull_raiseError() {
        val email: String? = null

        assertThatCode { userUpdateValidationService.validateEmail(email).block() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Email cannot be empty or blank.")
    }

    @Test
    fun validateEmail_whenEmailIsBlank_raiseError() {
        val email = "   "

        assertThatCode { userUpdateValidationService.validateEmail(email).block() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Email cannot be empty or blank.")
    }

    @Test
    fun validateEmail_whenEmailFormatIsInvalid_raiseError() {
        val email = "invalid-email"

        whenever(accountConfig.emailRegex).thenReturn("^[A-Za-z0-9+_.-]+@(.+)$")

        assertThatCode { userUpdateValidationService.validateEmail(email).block() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Invalid email format.")
    }

    @Test
    fun validateEmail_whenEmailIsValid_returnResult() {
        val email = "valid.email@example.com"

        whenever(accountConfig.emailRegex).thenReturn("^[A-Za-z0-9+_.-]+@(.+)$")

        whenever(userRepository.existsByEmailOrUsername(email, "")).thenReturn(Mono.just(false))

        val result = userUpdateValidationService.validateEmail(email).block()

        assertThat(result).isEqualTo(Result.VALID)
    }

    @Test
    fun validateNameLength_whenNameIsNull_raiseError() {
        val name: String? = null
        val fieldName = "First Name"

        assertThatCode { userUpdateValidationService.validateNameLength(name, fieldName).block() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("$fieldName cannot be empty or blank.")
    }

    @Test
    fun validateNameLength_whenNameIsTooShort_raiseError() {
        val name = "A"
        val fieldName = "First Name"

        whenever(accountConfig.nameMinLength).thenReturn(3)
        whenever(accountConfig.nameMaxLength).thenReturn(15)

        assertThatCode { userUpdateValidationService.validateNameLength(name, fieldName).block() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("$fieldName must be between 3 and 15 characters.")

    }

    @Test
    fun validateNameLength_whenNameIsValid_returnResult() {
        val name = "Valid Name"
        val fieldName = "First Name"

        whenever(accountConfig.nameMinLength).thenReturn(3)
        whenever(accountConfig.nameMaxLength).thenReturn(15)

        val result = userUpdateValidationService.validateNameLength(name, fieldName).block()

        assertThat(result).isEqualTo(Result.VALID)
    }
}
