package dev.hrmn.thrsqrspring.domain.service

import dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils.capitalize

@Service
class ResponseDomainService {
    fun capitaliseUsernames(responses: List<ResponseDto>) {
        responses.forEach { it.username = capitalize(it.username) }
    }
}