package user_service.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import user_service.model.CreateUserRequest
import user_service.model.Result
import user_service.repository.UserRepository

@Service
class UserCreationService(
    private val userRepository: UserRepository,
    private val userCreationValidationService: UserCreationValidationService
) {

    fun createUser(createUserRequest: CreateUserRequest): Mono<Result> {
        return userCreationValidationService.validate(createUserRequest)
            .flatMap { validationResult ->
                when (validationResult) {
                    is Result.VALID -> { userRepository.save(createUserRequest.toUser()).then(Mono.just(validationResult)) }
                    is Result.INVALID -> { Mono.just(validationResult) }
                }
            }.onErrorResume { Mono.just(Result.INVALID("Error occurred, try again!")) }
    }
}
