import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users")
data class User(
    @Id
    val id: Long? = null,
    val username: String,
    val email: String,
    val passwordHash: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val bio: String? = null,
    val profilePictureUrl: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
