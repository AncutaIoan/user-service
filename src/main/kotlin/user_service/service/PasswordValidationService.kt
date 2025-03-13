package user_service.service

import org.springframework.stereotype.Service
import user_service.config.PasswordConfig

@Service
class PasswordValidationService(
    private val passwordConfig: PasswordConfig
) {
    fun validate(password: String) {
        require(password.length in passwordConfig.minLength..passwordConfig.maxLength) { "Password must be between ${passwordConfig.minLength} and ${passwordConfig.maxLength} characters long." }
        require(!passwordConfig.requireSpecialChar || password.any { !it.isLetterOrDigit() }) { "Password must contain at least one special character." }
        require(!passwordConfig.requireUppercase || password.any { it.isUpperCase() }) { "Password must contain at least one uppercase letter." }
        require(!passwordConfig.requireDigit || password.any { it.isDigit() }) { "Password must contain at least one digit." }
    }
}