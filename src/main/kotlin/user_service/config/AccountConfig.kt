package user_service.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.constraints.account")
data class AccountConfig(
    val emailRegex: String,
    val usernameMinLength: Int = 3,
    val usernameMaxLength: Int = 20,
    val nameMinLength: Int = 1,
    val nameMaxLength: Int = 50
)
