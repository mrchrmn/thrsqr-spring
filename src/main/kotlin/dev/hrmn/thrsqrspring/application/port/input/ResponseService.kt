package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Response
import org.springframework.stereotype.Service

@Service
interface ResponseService {
    fun saveResponse(response: Response): Response
    fun deleteResponseById(id: Long)
    fun deleteAllResponsesFromEvent(event: Event)
    fun getResponsesByEvent(event: Event): List<ResponseDto>
}