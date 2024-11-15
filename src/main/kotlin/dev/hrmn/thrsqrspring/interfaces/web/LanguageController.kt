package dev.hrmn.thrsqrspring.interfaces.web

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LanguageController {
    @GetMapping("/change-language")
    fun changeLanguage(
        session: HttpSession,
        request: HttpServletRequest,
    ): String {
        val newLanguage = if (session.getAttribute("language") == "en") "de" else "en"
        session.setAttribute("language", newLanguage)

        val referer = request.getHeader("Referer") ?: "/"
        return "redirect:$referer"
    }
}
