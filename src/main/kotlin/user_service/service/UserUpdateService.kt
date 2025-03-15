package user_service.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import user_service.model.Result
import user_service.repository.UserRepository

@Service
class UserUpdateService(private val userRepository: UserRepository, private val userUpdateValidationService: UserUpdateValidationService) {
//    fun updateEmail(email: String, userInfo): Result {
//        userUpdateValidationService.validateEmail(email)
//            .map {
//                when(it) {
//                    Result.VALID -> updateEmailInDb(email)
//                    else -> it
//                }
//            }
//    }
//
//    private fun updateEmailInDb(email: String): Result {
//        userRepository.
//    }
}

