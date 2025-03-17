package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.output.persistence.ResponseJpaAdapter
import dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto
import dev.hrmn.thrsqrspring.application.port.input.ResponseService
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Participant
import dev.hrmn.thrsqrspring.domain.model.Response
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResponseService(private val responseJpaAdapter: ResponseJpaAdapter) : ResponseService {

    @Transactional
    override fun createOrUpdateResponse(
        participant: Participant,
        event: Event,
        comment: String,
        there: Boolean
    ): Response {
        val existingResponse = responseJpaAdapter.findByParticipantAndEvent(participant, event)

        return if (existingResponse !== null) {
            existingResponse.apply {
                this.comment = comment
                this.there = there
            }.let { responseJpaAdapter.save(it) }
        } else {
            Response(
                event = event,
                comment = comment,
                participant = participant,
                there = there
            ).let { responseJpaAdapter.save(it) }
        }
    }

    override fun findByEvent(event: Event): List<ResponseDto> {
        return responseJpaAdapter.findDtoByEvent(event)
    }

    override fun deleteById(id: Long) {
        responseJpaAdapter.deleteById(id)
    }

    override fun deleteAllFromEvent(event: Event) {
        responseJpaAdapter.deleteByEvent(event)
    }
}