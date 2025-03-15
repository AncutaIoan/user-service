package user_service.repository


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import user_service.test_configuration.RunSql
import user_service.test_configuration.TestcontainersConfigurationBase
import user_service.test_configuration.TestcontainersIntegrationTest

@TestcontainersIntegrationTest
class UserRepositoryIntegrationTest: TestcontainersConfigurationBase() {
    @Autowired
    lateinit var repository: UserRepository


    @Test
    @RunSql(["postgres/scripts/insert_user.sql"])
    fun existsByEmailOrUsername() {
        assertThat(repository.existsByEmailOrUsername("someemail@gmail.com", "username").block()).isFalse
        assertThat(repository.existsByEmailOrUsername("johndoe@example.com", "username").block()).isTrue
        assertThat(repository.existsByEmailOrUsername("someemail@gmail.com", "johndoe").block()).isTrue
        assertThat(repository.existsByEmailOrUsername("johndoe@example.com", "johndoe").block()).isTrue
    }
}
