package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Participant
import dev.hrmn.thrsqrspring.domain.model.Response
import org.springframework.stereotype.Service

@Service
interface ResponseService {
    fun createOrUpdateResponse(participant: Participant, event: Event, comment: String, there: Boolean): Response
    fun deleteById(id: Long)
}