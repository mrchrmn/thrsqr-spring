package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.adapter.output.persistence.jpa.JpaResponseRepository
import dev.hrmn.thrsqrspring.application.port.output.ResponseRepository
import dev.hrmn.thrsqrspring.domain.dto.ResponseDto

class ResponseRepository(private val jpaResponseRepository: JpaResponseRepository) : ResponseRepository {
    override fun findByEventCode(code: String): List<ResponseDto> {
        return jpaResponseRepository.findByEventCode(code)
    }
}