package user_service.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import user_service.test_configuration.TestcontainersConfigurationBase

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
class DebugControllerTest: TestcontainersConfigurationBase() {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun ping_returnsPong() {
        webTestClient.get().uri("/ping")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("pong")
    }
}