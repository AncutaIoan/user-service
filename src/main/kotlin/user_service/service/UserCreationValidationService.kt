package user_service.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import user_service.model.CreateUserRequest
import user_service.model.ValidationResult
import user_service.repository.UserRepository

@Service
class UserCreationValidationService(
    private val userRepository: UserRepository,
    private val passwordValidationService: PasswordValidationService
) {
    fun validate(request: CreateUserRequest): Mono<ValidationResult> {
        return Mono.zip(
            userRepository.existsByEmailOrUsername(request.email, request.username),
            passwordValidationService.validate(request.password)
        ).map { validations ->
            if(validations.t1 && validations.t2) {
                ValidationResult.VALID
            } else ValidationResult.INVALID
        }
    }
}
