package user_service.service

import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import user_service.model.CreateUserRequest
import user_service.model.LoginRequest
import user_service.model.LoginResponse
import user_service.model.Result
import user_service.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userCreationValidationService: UserCreationValidationService,
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

    fun loginBy(loginRequest: LoginRequest): Mono<ResponseEntity<LoginResponse>> =
        userRepository.findByEmail(loginRequest.email)
            .filter { match(loginRequest.password, it.passwordHash) }
            .map { user -> ResponseEntity.ok(LoginResponse.fromUser(user)) }
            .defaultIfEmpty(ResponseEntity.notFound().build())


    private fun match(password: String, hashedPassword: String): Boolean {
        val passwordEncoder = BCryptPasswordEncoder()
        return passwordEncoder.matches(password, hashedPassword)
    }
}