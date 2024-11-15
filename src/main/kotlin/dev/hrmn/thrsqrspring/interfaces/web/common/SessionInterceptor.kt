package dev.hrmn.thrsqrspring.interfaces.web.common

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor

class SessionInterceptor : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val session = request.session

        var sessionLanguage = session.getAttribute("language") as String?
        if (sessionLanguage == null) {
            sessionLanguage = request.getHeader("accept-Language")?.substring(0, 2) ?: "en"
            session.setAttribute("language", sessionLanguage)
        }

        return true
    }
}
