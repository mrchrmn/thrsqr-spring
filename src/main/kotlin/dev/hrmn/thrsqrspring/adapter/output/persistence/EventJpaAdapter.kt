package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.application.port.output.EventJpaRepository
import dev.hrmn.thrsqrspring.application.port.output.EventRepository
import dev.hrmn.thrsqrspring.domain.model.Event
import org.springframework.stereotype.Component

@Component
class EventJpaAdapter(private val eventJpaRepository: EventJpaRepository) : EventRepository {
    override fun save(event: Event): Event {
        return eventJpaRepository.save(event)
    }

    override fun findByCode(code: String): Event? {
        return eventJpaRepository.findByCode(code)
    }

    override fun updateLastUpdateToNow(event: Event) {
        eventJpaRepository.updateLastUpdateToNow(event.id)
    }
}