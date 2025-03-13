package user_service.service

import org.springframework.stereotype.Service
import user_service.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userCreationValidationService: UserCreationValidationService
) {

//    fun createUser(createUserRequest: CreateUserRequest) {
//        userCreationValidationService.validate(createUserRequest)
//            .filter { it == ValidationResult.VALID }
//            .flatMap { userRepository.save(createUserRequest.toUser()) }
//    }

}
