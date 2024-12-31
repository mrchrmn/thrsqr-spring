package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.adapter.output.persistence.jpa.JpaResponseRepository
import dev.hrmn.thrsqrspring.application.port.output.ResponseRepository
import dev.hrmn.thrsqrspring.domain.dto.ResponseDto
import dev.hrmn.thrsqrspring.domain.model.Event
import org.springframework.stereotype.Repository

@Repository
class ResponseRepository(private val jpaResponseRepository: JpaResponseRepository) : ResponseRepository {
    override fun findByEvent(event: Event): List<ResponseDto> {
        return jpaResponseRepository.findByEventCode(event.code)
    }

    override fun deleteByEvent(event: Event) {
        jpaResponseRepository.deleteByEvent(event)
    }
}