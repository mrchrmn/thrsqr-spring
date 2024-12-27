package dev.hrmn.thrsqrspring.adapter.input.web.common

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class GlobalModelAttributes {
    @ModelAttribute("currentURL")
    fun currentUrl(request: HttpServletRequest): StringBuffer = request.requestURL

    @ModelAttribute("origin")
    fun origin(request: HttpServletRequest): String {
        val scheme = request.scheme
        val serverName = request.serverName
        val serverPort = request.serverPort
        return "$scheme://$serverName:$serverPort"
    }
}
