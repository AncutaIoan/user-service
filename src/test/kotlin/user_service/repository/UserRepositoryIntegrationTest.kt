package user_service.repository


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.test.StepVerifier
import user_service.config.RunSql
import user_service.config.TestcontainersConfigurationBase
import user_service.config.TestcontainersIntegrationTest

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