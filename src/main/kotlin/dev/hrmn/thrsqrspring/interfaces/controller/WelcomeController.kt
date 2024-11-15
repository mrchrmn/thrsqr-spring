package dev.hrmn.thrsqrspring.interfaces.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.nio.file.Files
import java.nio.file.Paths

@Controller
class WelcomeController {
    @GetMapping("/")
    fun welcome(
        model: Model,
        session: HttpSession,
        request: HttpServletRequest,
    ): String {
        var sessionLanguage = session.getAttribute("language") as String?
        if (sessionLanguage == null) {
            sessionLanguage = request.getHeader("accept-Language")?.substring(0, 2) ?: "en"
            session.setAttribute("language", sessionLanguage)
        }

        var localizedLanguage = "en"
        if (sessionLanguage == "de") {
            localizedLanguage = "de"
        }

        val texts = readTextsJson()
        val localizedTexts = texts["texts"]?.get(localizedLanguage)
        model.addAttribute("language", sessionLanguage)
        model.addAttribute("TEXTS", localizedTexts)
        return "welcome"
    }

    private fun readTextsJson(): Map<String, Map<String, Map<String, Any>>> {
        val path = Paths.get("src/main/resources/locale/texts.json")
        val json = String(Files.readAllBytes(path))
        val mapper = jacksonObjectMapper()
        return mapper.readValue(json)
    }
}
