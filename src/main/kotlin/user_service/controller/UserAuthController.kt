package user_service.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import user_service.model.CreateUserRequest
import user_service.model.LoginRequest
import user_service.service.UserAuthService

@RestController
@RequestMapping("/api/auth")
class UserAuthController(
    private val userAuthService: UserAuthService
) {
    @PostMapping("/create")
    fun create(@RequestBody createUserRequest: CreateUserRequest) =
        userAuthService.createUser(createUserRequest)

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody loginRequest: LoginRequest) =
        userAuthService.authenticate(loginRequest)
}