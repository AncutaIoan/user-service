import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import user_service.config.PasswordConfig
import user_service.service.PasswordValidationService

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
    fun validate_whenPasswordTooShort_returnsFalse() {
        val result = service.validate("Short1!").block()
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun validate_whenPasswordTooLong_returnsFalse() {
        val result = service.validate("ThisPasswordIsWayTooLong1!").block()
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun validate_whenMissingSpecialCharacter_returnsFalse() {
        val result = service.validate("Password1").block()
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun validate_whenMissingUppercaseLetter_returnsFalse() {
        val result = service.validate("password1!").block()
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun validate_whenMissingDigit_returnsFalse() {
        val result = service.validate("Password!").block()
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun validate_whenValidPassword_returnsTrue() {
        val result = service.validate("Valid1pass!").block()
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun validate_whenNoSpecialCharRequired_returnsTrue() {
        val relaxedConfig = passwordConfig.copy(requireSpecialChar = false)
        val relaxedService = PasswordValidationService(relaxedConfig)
        val result = relaxedService.validate("ValidPass1").block()
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun validate_whenNoUppercaseRequired_returnsTrue() {
        val relaxedConfig = passwordConfig.copy(requireUppercase = false)
        val relaxedService = PasswordValidationService(relaxedConfig)
        val result = relaxedService.validate("validpass1!").block()
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun validate_whenNoDigitRequired_returnsTrue() {
        val relaxedConfig = passwordConfig.copy(requireDigit = false)
        val relaxedService = PasswordValidationService(relaxedConfig)
        val result = relaxedService.validate("ValidPass!").block()
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun validate_whenAllRequirementsDisabled_returnsTrue() {
        val relaxedConfig = passwordConfig.copy(
            requireSpecialChar = false,
            requireUppercase = false,
            requireDigit = false
        )
        val relaxedService = PasswordValidationService(relaxedConfig)
        val result = relaxedService.validate("anypassword").block()
        assertThat(result).isEqualTo(true)
    }
}
