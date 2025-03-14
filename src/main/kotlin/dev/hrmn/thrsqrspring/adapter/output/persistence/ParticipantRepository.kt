package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.adapter.output.persistence.jpa.JpaParticipantRepository
import dev.hrmn.thrsqrspring.application.port.output.ParticipantRepository
import dev.hrmn.thrsqrspring.domain.model.Participant
import org.springframework.stereotype.Repository

@Repository
class ParticipantRepository(private val jpaParticipantRepository: JpaParticipantRepository) : ParticipantRepository {
    override fun save(participant: Participant): Participant {
        return jpaParticipantRepository.save(participant)
    }
}