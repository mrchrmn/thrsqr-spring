package dev.hrmn.thrsqrspring.adapter.output.persistence.jpa

import dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Response
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface JpaResponseRepository : JpaRepository<Response, Long> {
    @Query(
        "SELECT new dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto(r.id, r.there, r.participant.username, r.comment) " +
                "FROM Response r WHERE r.event = :event"
    )
    fun findDtoByEvent(@Param("event") event: Event): List<ResponseDto>

    fun deleteByEvent(event: Event)
}