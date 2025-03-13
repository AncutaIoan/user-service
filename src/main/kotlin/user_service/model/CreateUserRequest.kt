package user_service.model

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import user_service.repository.entity.User

data class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val bio: String? = null,
    val profilePictureUrl: String? = null
) {

    fun toUser(): User =
        User(
            username = username,
            email = email,
            passwordHash = hashPassword(password),
            firstName = firstName,
            lastName = lastName,
            bio = bio,
            profilePictureUrl = profilePictureUrl
        )


    private fun hashPassword(password: String): String {
        val passwordEncoder = BCryptPasswordEncoder()
        return passwordEncoder.encode(password)
    }
}
