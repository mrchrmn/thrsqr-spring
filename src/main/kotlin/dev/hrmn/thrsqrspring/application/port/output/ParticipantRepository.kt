package dev.hrmn.thrsqrspring.application.port.output

import dev.hrmn.thrsqrspring.domain.model.Participant

interface ParticipantRepository {
    fun save(participant: Participant): Participant
}