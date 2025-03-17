package user_service.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest) =
        userService.loginBy(loginRequest)
}