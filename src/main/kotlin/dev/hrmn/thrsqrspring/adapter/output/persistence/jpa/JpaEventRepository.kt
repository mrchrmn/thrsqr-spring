package dev.hrmn.thrsqrspring.adapter.output.persistence.jpa

import dev.hrmn.thrsqrspring.domain.model.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface JpaEventRepository : JpaRepository<Event, Long> {
    fun findByCode(code: String): Event?

    @Modifying
    @Query("UPDATE Event e SET e.lastUpdate = CURRENT_TIMESTAMP WHERE e.id = :eventId")
    fun updateLastUpdateToNow(@Param("eventId") eventId: Long)
}