package dev.hrmn.thrsqrspring.application.port.output

import dev.hrmn.thrsqrspring.domain.model.Participant
import java.util.*

interface ParticipantRepository {
    fun save(participant: Participant): Participant
    fun findById(participantId: Long): Optional<Participant>
}