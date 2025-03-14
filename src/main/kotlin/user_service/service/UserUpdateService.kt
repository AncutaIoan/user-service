package user_service.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import user_service.model.Result
import user_service.repository.UserRepository

//@Service
//class UserUpdateService(private val userRepository: UserRepository, private val userUpdateValidationService: UserUpdateValidationService) {
//    fun updateProfilePicture(): Mono<Result> {
//        return Result.VALID.toMono();
//    }
//
//    fun updateBio(newBio: String?): Mono<Result> {
//
//        return Result.VALID.toMono();
//    }
//
//    fun updateUserProfile(updateUserRequest: UpdateUserRequest): Mono<Result> {
//        userUpdateValidationService.validate(updateUserRequest)
//            .map {
//                userRepository
//            }
//        return Result.VALID.toMono();
//    }
//}

