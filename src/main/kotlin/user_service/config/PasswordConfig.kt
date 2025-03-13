package user_service.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.password")
data class PasswordConfig(
    var minLength: Int = 8,
    var maxLength: Int = 20,
    var requireSpecialChar: Boolean = true,
    var requireUppercase: Boolean = true,
    var requireDigit: Boolean = true
)
