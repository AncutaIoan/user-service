package user_service.repository


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.test.StepVerifier
import user_service.config.TestcontainersConfigurationBase
import user_service.config.TestcontainersIntegrationTest

@TestcontainersIntegrationTest
class UserRepositoryIntegrationTest: TestcontainersConfigurationBase() {
    @Autowired
    private lateinit var repository: UserRepository

    @Test
    fun testFindByEmail() {
        StepVerifier.create(repository.findUserByEmail("test@test.com"))
            .expectNextCount(0) // Expect no results
            .verifyComplete()    }
}