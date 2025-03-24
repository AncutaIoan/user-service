package user_service.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import user_service.model.CreateUserRequest
import user_service.model.LoginRequest
import user_service.model.UserPayload
import user_service.model.Result
import user_service.repository.UserRepository

@Service
class UserAuthService(
    private val userRepository: UserRepository,
    private val userCreationValidationService: UserCreationValidationService,
) {

    fun createUser(createUserRequest: CreateUserRequest) =
        userCreationValidationService.validate(createUserRequest)
            .flatMap { validationResult ->
                when (validationResult) {
                    is Result.VALID -> { userRepository.save(createUserRequest.toUser()).then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build())) }
                    is Result.INVALID -> { Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResult)) }
                }
            }
            .onErrorResume { Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.INVALID("Error occurred, try again!"))) }


    fun authenticate(loginRequest: LoginRequest): Mono<ResponseEntity<UserPayload>> =
        userRepository.findByEmail(loginRequest.email)
            .filter { match(loginRequest.password, it.passwordHash) }
            .map { user -> ResponseEntity.ok(UserPayload.fromUser(user)) }
            .defaultIfEmpty(ResponseEntity.notFound().build())


    private fun match(password: String, hashedPassword: String): Boolean {
        val passwordEncoder = BCryptPasswordEncoder()
        return passwordEncoder.matches(password, hashedPassword)
    }
}