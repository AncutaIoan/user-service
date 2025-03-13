package user_service.model

sealed class Result {
    data object VALID : Result()
    data class INVALID(val message: String) : Result()
}
