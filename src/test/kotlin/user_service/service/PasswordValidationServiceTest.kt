package user_service.service

import org.assertj.core.api.Assertions.assertThatThrownBy
import user_service.config.PasswordConfig
import kotlin.test.Test

internal class PasswordValidationServiceTest {

    private val passwordConfig = PasswordConfig(
        minLength = 8,
        maxLength = 16,
        requireSpecialChar = true,
        requireUppercase = true,
        requireDigit = true
    )

    private val service = PasswordValidationService(passwordConfig)

    @Test
    fun validate_whenPasswordTooShort_throwsErrorWith() {
        assertThatThrownBy { service.validate("Short1!") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Password must be between 8 and 16 characters long.")
    }

    @Test
    fun validate_whenPasswordTooLong_throwsErrorWith() {
        assertThatThrownBy { service.validate("ThisPasswordIsWayTooLong1!") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Password must be between 8 and 16 characters long.")
    }

    @Test
    fun validate_whenMissingSpecialCharacter_throwsErrorWith() {
        assertThatThrownBy { service.validate("Password1") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Password must contain at least one special character.")
    }

    @Test
    fun validate_whenMissingUppercaseLetter_throwsErrorWith() {
        assertThatThrownBy { service.validate("password1!") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Password must contain at least one uppercase letter.")
    }

    @Test
    fun validate_whenMissingDigit_throwsErrorWith() {
        assertThatThrownBy { service.validate("Password!") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Password must contain at least one digit.")
    }

    @Test
    fun validate_whenValidPassword_doesNotThrowError() {
        service.validate("Valid1pass!")
    }

    @Test
    fun validate_whenNoSpecialCharRequired_doesNotThrowError() {
        val relaxedConfig = passwordConfig.copy(requireSpecialChar = false)
        val relaxedService = PasswordValidationService(relaxedConfig)
        relaxedService.validate("ValidPass1")
    }

    @Test
    fun validate_whenNoUppercaseRequired_doesNotThrowError() {
        val relaxedConfig = passwordConfig.copy(requireUppercase = false)
        val relaxedService = PasswordValidationService(relaxedConfig)
        relaxedService.validate("validpass1!")
    }

    @Test
    fun validate_whenNoDigitRequired_doesNotThrowError() {
        val relaxedConfig = passwordConfig.copy(requireDigit = false)
        val relaxedService = PasswordValidationService(relaxedConfig)
        relaxedService.validate("ValidPass!")
    }

    @Test
    fun validate_whenAllRequirementsDisabled_doesNotThrowError() {
        val relaxedConfig = passwordConfig.copy(
            requireSpecialChar = false,
            requireUppercase = false,
            requireDigit = false
        )
        val relaxedService = PasswordValidationService(relaxedConfig)
        relaxedService.validate("anypassword")
    }
}