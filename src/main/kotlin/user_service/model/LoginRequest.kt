package user_service.model

data class LoginRequest(
    val email: String,
    val password: String
)