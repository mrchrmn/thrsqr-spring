package dev.hrmn.thrsqrspring.adapter.output

import dev.hrmn.thrsqrspring.application.port.output.EventRepository
import dev.hrmn.thrsqrspring.domain.model.Event
import org.springframework.stereotype.Repository

@Repository
class EventRepository(private val jpaEventRepository: JpaEventRepository) : EventRepository {
    override fun save(event: Event): Event {
        return jpaEventRepository.save(event)
    }
}