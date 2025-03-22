package user_service.model

import user_service.repository.entity.User

data class UserDto (
    val username: String,
    val email: String,
    val firstName: String?,
    val lastName: String?,
    val bio: String?,
    val profilePicture: String?
) {
    companion object {
        fun from(user: User): UserDto {
            return UserDto(
                user.username,
                user.email,
                user.firstName,
                user.lastName,
                user.bio,
                user.profilePictureUrl
            )
        }
    }
}
