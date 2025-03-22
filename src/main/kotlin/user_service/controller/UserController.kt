package user_service.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.kotlin.core.publisher.toMono
import user_service.model.CreateUserRequest
import user_service.model.LoginRequest
import user_service.service.UserService

@RestController
@RequestMapping("/api/auth")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/create")
    fun create(@RequestBody createUserRequest: CreateUserRequest) =
        userService.createUser(createUserRequest)

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody loginRequest: LoginRequest) =
        userService.authenticate(loginRequest)

    @GetMapping("/test-endpoint")
    fun testEndpoint(@RequestHeader(value = "X-JWS-Payload", required = false) jwsPayload: String?) = ResponseEntity.ok().body(jwsPayload).toMono()
}