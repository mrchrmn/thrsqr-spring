package dev.hrmn.thrsqrspring.application.port.output

import dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Participant
import dev.hrmn.thrsqrspring.domain.model.Response

interface ResponseRepository {
    fun findDtoByEvent(event: Event): List<ResponseDto>
    fun save(response: Response): Response
    fun deleteByEvent(event: Event)
    fun deleteById(id: Long)
    fun findByParticipantAndEvent(participant: Participant, event: Event): Response?
}