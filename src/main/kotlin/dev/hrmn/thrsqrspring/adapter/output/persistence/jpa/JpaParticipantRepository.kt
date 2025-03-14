package dev.hrmn.thrsqrspring.adapter.output.persistence.jpa

import dev.hrmn.thrsqrspring.domain.model.Participant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaParticipantRepository : JpaRepository<Participant, String>