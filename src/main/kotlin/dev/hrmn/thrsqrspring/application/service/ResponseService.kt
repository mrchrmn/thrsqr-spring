package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.output.persistence.ResponseRepository
import dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto
import dev.hrmn.thrsqrspring.application.port.input.ResponseService
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Response
import org.springframework.stereotype.Service

@Service
class ResponseService(private val responseRepository: ResponseRepository) : ResponseService {
    override fun saveResponse(response: Response): Response {
        return responseRepository.save(response)
    }

    override fun getResponsesByEvent(event: Event): List<ResponseDto> {
        return responseRepository.findDtoByEvent(event)
    }

    override fun deleteResponseById(id: Long) {
        responseRepository.deleteById(id)
    }

    override fun deleteAllResponsesFromEvent(event: Event) {
        responseRepository.deleteByEvent(event)
    }
}