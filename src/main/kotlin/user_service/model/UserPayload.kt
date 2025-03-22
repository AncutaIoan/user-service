package user_service.model

import user_service.repository.entity.User

data class UserPayload(
    val userId: Long
) {
    companion object {
        fun fromUser(user: User) =
            user.id?.let { UserPayload(userId = it) }
    }
}