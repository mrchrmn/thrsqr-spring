package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.output.persistence.ParticipantJpaAdapter
import dev.hrmn.thrsqrspring.application.port.input.ParticipantService
import dev.hrmn.thrsqrspring.domain.model.Participant
import org.springframework.stereotype.Service

@Service
class ParticipantService(private val participantJpaAdapter: ParticipantJpaAdapter) : ParticipantService {

    override fun getParticipantById(participantId: Long): Participant {
        return participantJpaAdapter.findById(participantId)
            .orElseThrow { IllegalArgumentException("Participant not found") }
    }

    override fun createOrUpdateParticipant(id: Long?, username: String): Participant {
        return if (id == null) {
            Participant(username = username)
        } else {
            getParticipantById(id).apply { this.username = username }
        }.let { participantJpaAdapter.save(it) }
    }
}