package user_service.service

import com.sun.org.slf4j.internal.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import user_service.config.PasswordConfig

@Service
class PasswordValidationService(private val passwordConfig: PasswordConfig) {

    private val logger = LoggerFactory.getLogger(PasswordValidationService::class.java)

    fun validate(password: String): Mono<Boolean> {
        return Mono.fromCallable {
            validateLength(password)
            validateSpecialCharacter(password)
            validateUppercase(password)
            validateDigit(password)
            true
        }.onErrorResume { e ->
            logger.error("Password validation failed: ${e.message}", e)
            Mono.just(false)
        }
    }

    private fun validateLength(password: String) =
        require(password.length in passwordConfig.minLength..passwordConfig.maxLength) { "Password must be between ${passwordConfig.minLength} and ${passwordConfig.maxLength} characters long." }


    private fun validateSpecialCharacter(password: String) =
        require(!(passwordConfig.requireSpecialChar && password.none { !it.isLetterOrDigit() })) { "Password must contain at least one special character." }


    private fun validateUppercase(password: String)=
        require(!(passwordConfig.requireUppercase && password.none { it.isUpperCase() })) { "Password must contain at least one uppercase letter." }

    private fun validateDigit(password: String) =
        require(!(passwordConfig.requireDigit && password.none { it.isDigit() })) { "Password must contain at least one digit." }

}
