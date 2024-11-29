package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.application.port.input.WelcomeController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WelcomeController: WelcomeController {
    @GetMapping("/")
    override fun displayWelcomePage(): String = "welcome"
}
