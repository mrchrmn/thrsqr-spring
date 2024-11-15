package dev.hrmn.thrsqrspring.interfaces.web.common

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.servlet.http.HttpSession
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute
import java.nio.file.Files
import java.nio.file.Paths

@ControllerAdvice
class GlobalModelAttributes {
    @ModelAttribute("language")
    fun language(session: HttpSession): String = session.getAttribute("language") as String

    @ModelAttribute("TEXTS")
    fun interfaceTexts(session: HttpSession): Map<String, Any>? {
        val sessionLanguage = session.getAttribute("language")

        var localizedLanguage = "en"
        if (sessionLanguage == "de") {
            localizedLanguage = "de"
        }

        val texts = readTextsJson()
        val localizedTexts = texts["texts"]?.get(localizedLanguage)

        return localizedTexts
    }

    private fun readTextsJson(): Map<String, Map<String, Map<String, Any>>> {
        val path = Paths.get("src/main/resources/locale/texts.json")
        val json = String(Files.readAllBytes(path))
        val mapper = jacksonObjectMapper()
        return mapper.readValue(json)
    }
}
