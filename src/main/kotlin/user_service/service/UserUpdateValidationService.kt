package user_service.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import user_service.config.AccountConfig
import user_service.model.Result
import user_service.repository.UserRepository

@Service
class UserUpdateValidationService(
    private val userRepository: UserRepository,
    private val accountConfig: AccountConfig
) {
    fun validateUsername(username: String?): Mono<Result> {
        if (username.isNullOrBlank()) {
            return Mono.error(IllegalArgumentException("Username cannot be empty or blank."))
        }

        if (username.length !in accountConfig.usernameMinLength..accountConfig.usernameMaxLength) {
            return Mono.error(IllegalArgumentException("Username must be between ${accountConfig.usernameMinLength} and ${accountConfig.usernameMaxLength} characters."))
        }

        if (!username.all { it.isLetterOrDigit() || it == '_' }) {
            return Mono.error(IllegalArgumentException("Username can only contain letters, digits, and underscores."))
        }

        return userRepository.existsByEmailOrUsername("", username)
            .flatMap { exists ->
                if (exists) Mono.error(IllegalArgumentException("Username is already taken."))
                else Mono.just(Result.VALID)
            }
    }

    fun validateEmail(email: String?): Mono<Result> {
        if (email.isNullOrBlank()) {
            return Mono.error(IllegalArgumentException("Email cannot be empty or blank."))
        }

        if (!email.matches(accountConfig.emailRegex.toRegex())) {
            return Mono.error(IllegalArgumentException("Invalid email format."))
        }

        return userRepository.existsByEmailOrUsername(email, "")
            .flatMap { exists ->
                if (exists) Mono.error(IllegalArgumentException("Email is already in use."))
                else Mono.just(Result.VALID)
            }
    }

    fun validateNameLength(name: String?, fieldName: String): Mono<Result> {
        if (name.isNullOrBlank()) {
            return Mono.error(IllegalArgumentException("$fieldName cannot be empty or blank."))
        }

        if (name.length !in accountConfig.nameMinLength..accountConfig.nameMaxLength) {
            return Mono.error(IllegalArgumentException("$fieldName must be between ${accountConfig.nameMinLength} and ${accountConfig.nameMaxLength} characters."))
        }

        return Mono.just(Result.VALID)
    }
}
