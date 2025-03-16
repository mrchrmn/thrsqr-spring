package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.application.port.input.LegalController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LegalController : LegalController {
    @GetMapping("/legal")
    override fun displayLegalPage(): String = "legal"
}
