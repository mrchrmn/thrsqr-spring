package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.adapter.output.persistence.jpa.JpaResponseRepository
import dev.hrmn.thrsqrspring.application.port.output.ResponseRepository
import dev.hrmn.thrsqrspring.domain.dto.ResponseDto
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Participant
import dev.hrmn.thrsqrspring.domain.model.Response
import org.springframework.stereotype.Repository

@Repository
class ResponseRepository(private val jpaResponseRepository: JpaResponseRepository) : ResponseRepository {
    override fun findDtoByEvent(event: Event): List<ResponseDto> {
        return jpaResponseRepository.findDtoByEvent(event)
    }

    override fun deleteByEvent(event: Event) {
        jpaResponseRepository.deleteByEvent(event)
    }

    override fun deleteByEventAndParticipant(event: Event, participant: Participant) {
        jpaResponseRepository.deleteByEventAndParticipant(event, participant)
    }

    override fun save(response: Response) {
        jpaResponseRepository.save(response)
    }
}