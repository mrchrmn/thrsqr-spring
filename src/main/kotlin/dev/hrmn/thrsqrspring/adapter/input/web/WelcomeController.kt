package dev.hrmn.thrsqrspring.adapter.input.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WelcomeController {
    @GetMapping("/")
    fun welcome(): String = "welcome"
}
