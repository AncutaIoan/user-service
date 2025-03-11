package user_service.config

import io.r2dbc.spi.ConnectionFactory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@Tag("integration-test")
abstract class TestcontainersConfigurationBase {
	@Autowired
	lateinit var connectionFactory: ConnectionFactory


	companion object {
		private val log: Logger = LoggerFactory.getLogger(this::class.java)

		private val postgres: PostgreSQLContainer<*> = PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
			.apply {
				this.withDatabaseName("testDb")
					.withUsername("root")
					.withPassword("123456")
					.withReuse(true)
			}

		private val redis: GenericContainer<*> = GenericContainer(DockerImageName.parse("redis:latest"))
			.withExposedPorts(6379)



		fun r2dbcUrl(): String = "r2dbc:postgresql://${postgres.host}:${postgres.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT)}/${postgres.databaseName}"
		fun jdbcUrl(): String = "jdbc:postgresql://${postgres.host}:${postgres.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT)}/${postgres.databaseName}"

		@JvmStatic
		@BeforeAll
		internal fun setup(): Unit {
			postgres.start()
			log.info("Testcontainers -> PostgresSQL DB started on [${r2dbcUrl()}] with user: root and password:123456")
			redis.start()
		}

		@JvmStatic
		@DynamicPropertySource
		fun properties(registry: DynamicPropertyRegistry): Unit {
			registry.add("spring.r2dbc.url", ::r2dbcUrl)
			registry.add("spring.r2dbc.username", postgres::getUsername)
			registry.add("spring.r2dbc.password", postgres::getPassword)
			registry.add("spring.flyway.url", ::jdbcUrl)
		}
	}
}
