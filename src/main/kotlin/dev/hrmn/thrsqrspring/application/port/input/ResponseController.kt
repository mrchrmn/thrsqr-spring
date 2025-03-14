package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.RemoveResponseForm
import dev.hrmn.thrsqrspring.adapter.input.web.dto.ResponseForm

interface ResponseController {
    fun save(responseForm: ResponseForm): String
    fun remove(removeResponseForm: RemoveResponseForm): String
}