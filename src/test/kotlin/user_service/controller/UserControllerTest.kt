package user_service.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import user_service.test_configuration.RunSql
import user_service.test_configuration.RunSqlExtension
import user_service.test_configuration.TestcontainersConfigurationBase


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RunSqlExtension::class)
class UserControllerTest : TestcontainersConfigurationBase() {

    @Autowired
    lateinit var webTestClient: WebTestClient


    @Test
    @RunSql(["postgres/scripts/existing_user.sql"])
    fun me_whenUserFound_retrieveUserInfo() {
        webTestClient.get().uri("/user/me")
            .header("X-JWS-Payload", "1")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("{\"username\":\"john_doesds\",\"email\":\"johsds@example.com\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"bio\":\"I love Kotlin!\",\"profilePicture\":\"https://example.com/profile.jpg\"}")
    }
}