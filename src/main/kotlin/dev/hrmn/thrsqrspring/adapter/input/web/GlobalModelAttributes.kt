package dev.hrmn.thrsqrspring.adapter.input.web

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class GlobalModelAttributes {
    @ModelAttribute("currentURL")
    fun currentUrl(request: HttpServletRequest): StringBuffer = request.requestURL
}
