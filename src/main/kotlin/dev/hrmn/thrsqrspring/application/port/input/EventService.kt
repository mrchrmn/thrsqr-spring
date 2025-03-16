package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventViewModel
import dev.hrmn.thrsqrspring.adapter.input.web.dto.NewEventForm
import dev.hrmn.thrsqrspring.domain.model.Event

interface EventService {
    fun createNewEvent(newEventForm: NewEventForm): Event
    fun getEventViewModelByEventCode(code: String): EventViewModel
    fun getEventByEventCode(code: String): Event?
    fun updateLastUpdateToNow(event: Event)
}