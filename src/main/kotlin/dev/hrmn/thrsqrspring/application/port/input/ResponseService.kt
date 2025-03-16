package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Participant
import dev.hrmn.thrsqrspring.domain.model.Response
import org.springframework.stereotype.Service

@Service
interface ResponseService {
    fun save(response: Response): Response
    fun deleteById(id: Long)
    fun deleteAllFromEvent(event: Event)
    fun findByEvent(event: Event): List<ResponseDto>
    fun findByParticipantAndEvent(participant: Participant, event: Event): Response?
    fun createOrUpdateResponse(participant: Participant, event: Event, comment: String, there: Boolean): Response
}