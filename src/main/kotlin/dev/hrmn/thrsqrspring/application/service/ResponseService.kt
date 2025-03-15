package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.output.persistence.ResponseJpaAdapter
import dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto
import dev.hrmn.thrsqrspring.application.port.input.ResponseService
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Response
import org.springframework.stereotype.Service

@Service
class ResponseService(private val responseJpaAdapter: ResponseJpaAdapter) : ResponseService {
    override fun saveResponse(response: Response): Response {
        return responseJpaAdapter.save(response)
    }

    override fun getResponsesByEvent(event: Event): List<ResponseDto> {
        return responseJpaAdapter.findDtoByEvent(event)
    }

    override fun deleteResponseById(id: Long) {
        responseJpaAdapter.deleteById(id)
    }

    override fun deleteAllResponsesFromEvent(event: Event) {
        responseJpaAdapter.deleteByEvent(event)
    }
}