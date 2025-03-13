package user_service.repository


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.test.StepVerifier
import user_service.test_configuration.RunSql
import user_service.test_configuration.TestcontainersConfigurationBase
import user_service.test_configuration.TestcontainersIntegrationTest

@TestcontainersIntegrationTest
class UserRepositoryIntegrationTest: TestcontainersConfigurationBase() {
    @Autowired
    lateinit var repository: UserRepository


    @Test
    @RunSql([])
    fun testFindByEmail() {
        StepVerifier.create(repository.findById(1L ))
            .verifyComplete()
    }
}