package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto
import dev.hrmn.thrsqrspring.application.port.output.ResponseJpaRepository
import dev.hrmn.thrsqrspring.application.port.output.ResponseRepository
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Participant
import dev.hrmn.thrsqrspring.domain.model.Response
import org.springframework.stereotype.Repository

@Repository
class ResponseJpaAdapter(private val responseJpaRepository: ResponseJpaRepository) : ResponseRepository {
    override fun findDtoByEvent(event: Event): List<ResponseDto> {
        return responseJpaRepository.findDtoByEvent(event)
    }

    override fun findByParticipantAndEvent(participant: Participant, event: Event): Response? {
        return responseJpaRepository.findByParticipantAndEvent(participant, event)
    }

    override fun deleteByEvent(event: Event) {
        responseJpaRepository.deleteByEvent(event)
    }

    override fun deleteById(id: Long) {
        responseJpaRepository.deleteById(id)
    }

    override fun save(response: Response): Response {
        return responseJpaRepository.save(response)
    }
}