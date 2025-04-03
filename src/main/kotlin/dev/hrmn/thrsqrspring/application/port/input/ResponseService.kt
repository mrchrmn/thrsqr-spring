package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Participant
import dev.hrmn.thrsqrspring.domain.model.Response

interface ResponseService {
    fun createOrUpdateResponse(participant: Participant, event: Event, comment: String, there: Boolean): Response
    fun deleteById(id: Long)
}