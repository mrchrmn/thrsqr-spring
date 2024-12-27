package dev.hrmn.thrsqrspring.application.port.output

import dev.hrmn.thrsqrspring.domain.model.Event

interface EventRepository {
    fun save(event: Event): Event
    fun findByCode(code: String): Event?
}