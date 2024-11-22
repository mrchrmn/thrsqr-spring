package dev.hrmn.thrsqrspring.interfaces.web.common

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class GlobalModelAttributes {
    @ModelAttribute("currentURL")
    fun currentUrl(request: HttpServletRequest): StringBuffer = request.requestURL
}
