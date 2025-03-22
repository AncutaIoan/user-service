package user_service.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import user_service.service.UserService

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {
    @GetMapping("/me")
    fun getLoggedUser(@RequestHeader(value = "X-JWS-Payload", required = true) userId: Long) =
        userService.getUserById(userId)
}