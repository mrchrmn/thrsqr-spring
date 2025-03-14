package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto
import dev.hrmn.thrsqrspring.adapter.output.persistence.jpa.JpaResponseRepository
import dev.hrmn.thrsqrspring.application.port.output.ResponseRepository
import dev.hrmn.thrsqrspring.domain.model.Event
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

    override fun deleteById(id: Long) {
        jpaResponseRepository.deleteById(id)
    }

    override fun save(response: Response): Response {
        return jpaResponseRepository.save(response)
    }
}