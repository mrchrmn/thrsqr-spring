package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.domain.model.Participant

interface ParticipantService {
    fun getParticipantById(participantId: Long): Participant
    fun createNewParticipant(username: String): Participant
}