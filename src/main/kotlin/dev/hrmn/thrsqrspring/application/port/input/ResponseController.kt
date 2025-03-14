package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.DeleteResponseForm
import dev.hrmn.thrsqrspring.adapter.input.web.dto.ResponseForm

interface ResponseController {
    fun save(responseForm: ResponseForm): String
    fun delete(deleteResponseForm: DeleteResponseForm): String
}