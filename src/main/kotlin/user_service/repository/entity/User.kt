package user_service.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

@Table("users")
data class User(
    @Id
    val id: Long? = null,
    @Column("username")
    val username: String,
    @Column("email")
    val email: String,
    @Column("password_hash")
    val passwordHash: String,
    @Column("first_name")
    val firstName: String? = null,
    @Column("last_name")
    val lastName: String? = null,
    @Column("bio")
    val bio: String? = null,
    @Column("profile_picture_url")
    val profilePictureUrl: String? = null,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column("updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
