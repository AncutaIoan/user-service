package user_service.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2
import user_service.model.CreateUserRequest
import user_service.model.Result
import user_service.repository.UserRepository

@Service
class UserCreationValidationService(
    private val userRepository: UserRepository,
    private val passwordValidationService: PasswordValidationService
) {
    fun validate(request: CreateUserRequest): Mono<Result> {
        return Mono.zip(userRepository.existsByEmailOrUsername(request.email, request.username), passwordValidationService.validate(request.password))
            .map { (emailOrUsernameExists, passwordIsValid) ->
                    when {
                        emailOrUsernameExists -> Result.INVALID("Email or Username is already taken.")
                        !passwordIsValid -> Result.INVALID("Password is invalid. Please follow the password requirements.")
                        else -> Result.VALID
                    }
            }
    }
}
