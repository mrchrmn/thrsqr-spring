package dev.hrmn.thrsqrspring.application.port.output

import dev.hrmn.thrsqrspring.domain.dto.ResponseDto
import dev.hrmn.thrsqrspring.domain.model.Event

interface ResponseRepository {
    fun findByEvent(event: Event): List<ResponseDto>
    fun deleteByEvent(event: Event)
}