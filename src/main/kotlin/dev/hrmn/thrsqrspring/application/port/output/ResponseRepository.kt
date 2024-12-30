package dev.hrmn.thrsqrspring.application.port.output

import dev.hrmn.thrsqrspring.domain.dto.ResponseDto

interface ResponseRepository {
    fun findByEventCode(code: String): List<ResponseDto>
}