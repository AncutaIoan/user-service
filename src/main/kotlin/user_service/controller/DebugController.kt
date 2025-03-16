package user_service.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DebugController {
    @GetMapping("/ping")
    suspend fun ping(): String {
        return "pong"
    }
}