package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.adapter.output.persistence.jpa.JpaEventRepository
import dev.hrmn.thrsqrspring.application.port.output.EventRepository
import dev.hrmn.thrsqrspring.domain.model.Event
import org.springframework.stereotype.Repository

@Repository
class EventRepository(private val jpaEventRepository: JpaEventRepository) : EventRepository {
    override fun save(event: Event): Event {
        return jpaEventRepository.save(event)
    }

    override fun findByCode(code: String): Event? {
        return jpaEventRepository.findByCode(code)
    }

    override fun updateLastUpdateToNow(event: Event) {
        jpaEventRepository.updateLastUpdateToNow(event.id)
    }
}