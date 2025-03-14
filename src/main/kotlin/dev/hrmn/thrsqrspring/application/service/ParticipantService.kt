package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.output.persistence.ParticipantRepository
import dev.hrmn.thrsqrspring.application.port.input.ParticipantService
import dev.hrmn.thrsqrspring.domain.model.Participant
import org.springframework.stereotype.Service

@Service
class ParticipantService(private val participantRepository: ParticipantRepository) : ParticipantService {
    override fun getParticipantById(participantId: Long): Participant {
        return participantRepository.findById(participantId)
            .orElseThrow { IllegalArgumentException("Participant not found") }
    }

    override fun createNewParticipant(username: String): Participant {
        val newParticipant = Participant(username = username)
        return participantRepository.save(newParticipant)
    }
}