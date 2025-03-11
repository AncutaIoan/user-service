package repository

import adamicus.repository.UserRepository
import config.TestcontainersConfiguration
import config.TestcontainersIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

@TestcontainersIntegrationTest
internal class UserRepositoryIntegrationTest: TestcontainersConfiguration() {
    @Autowired
    lateinit var repository: UserRepository;
    
}