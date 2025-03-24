package user_service.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import user_service.test_configuration.TestcontainersConfigurationBase

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserAuthControllerTest : TestcontainersConfigurationBase() {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun createUser_whenSuccess_returnResultSuccess() {
        val body = """
                        {
                          "username": "ValidUser123",
                          "email": "user@example.com",
                          "password": "Str0ng!Pass",
                          "firstName": "John",
                          "lastName": "Doe",
                          "bio": "I am a software developer who loves coding!",
                          "profilePictureUrl": "https://example.com/profile.jpg"
                        }
                    """.trimIndent()

        webTestClient.post()
            .uri("/api/auth/create")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .exchange()
            .expectStatus().isCreated
    }


    @Test
    fun authenticate_whenSuccess_returnUserPayload() {
        val createUserRequest = """
                                    {
                                      "username": "ValidUser1234",
                                      "email": "user1@example.com",
                                      "password": "Str0ng!Pass",
                                      "firstName": "John",
                                      "lastName": "Doe",
                                      "bio": "I am a software developer who loves coding!",
                                      "profilePictureUrl": "https://example.com/profile.jpg"
                                    }
                                """.trimIndent()

        webTestClient.post()
            .uri("/api/auth/create")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(createUserRequest)
            .exchange()
            .expectStatus().isCreated

        val loginRequest = """
                                {
                                  "email": "user1@example.com",
                                  "password": "Str0ng!Pass"
                                }
                            """.trimIndent()

        webTestClient.post()
            .uri("/api/auth/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(loginRequest)
            .exchange()
            .expectStatus().isOk
    }
}
