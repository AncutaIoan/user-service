package user_service.model

import user_service.repository.entity.User

data class LoginResponse (
    val userId: Long?,
    val userName: String,
    val email: String
) {
    companion object {
        fun fromUser(user: User) =
            LoginResponse(
                userId = user.id,
                userName = user.username,
                email = user.email
            )
    }
}