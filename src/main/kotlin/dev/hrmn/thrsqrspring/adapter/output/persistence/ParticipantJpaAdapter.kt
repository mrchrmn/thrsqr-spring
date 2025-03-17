package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.application.port.output.ParticipantJpaRepository
import dev.hrmn.thrsqrspring.application.port.output.ParticipantRepository
import dev.hrmn.thrsqrspring.domain.model.Participant
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ParticipantJpaAdapter(private val participantJpaRepository: ParticipantJpaRepository) : ParticipantRepository {

    override fun save(participant: Participant): Participant {
        return participantJpaRepository.save(participant)
    }

    override fun findById(participantId: Long): Optional<Participant> {
        return participantJpaRepository.findById(participantId)
    }
}