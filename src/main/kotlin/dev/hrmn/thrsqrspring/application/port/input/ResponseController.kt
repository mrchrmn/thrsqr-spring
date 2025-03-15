package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.DeleteResponseForm
import dev.hrmn.thrsqrspring.adapter.input.web.dto.ResponseForm
import jakarta.servlet.http.HttpSession

interface ResponseController {
    fun save(responseForm: ResponseForm, session: HttpSession): String
    fun delete(deleteResponseForm: DeleteResponseForm, session: HttpSession): String
}