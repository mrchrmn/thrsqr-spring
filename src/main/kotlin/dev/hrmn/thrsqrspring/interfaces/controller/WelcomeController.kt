package dev.hrmn.thrsqrspring.interfaces.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.nio.file.Files
import java.nio.file.Paths

@Controller
class WelcomeController {
    @GetMapping("/")
    fun welcome(model: Model): String {
        val texts = readTextsJson()
        val language = "de" // or "en"
        val localizedTexts = texts["texts"]?.get(language)
        model.addAttribute("language", language)
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
