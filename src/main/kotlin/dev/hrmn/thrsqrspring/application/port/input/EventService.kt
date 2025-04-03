package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventEditViewModel
import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventForm
import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventViewModel
import dev.hrmn.thrsqrspring.domain.model.Event

interface EventService {
    fun createEvent(eventForm: EventForm): Event
    fun generateEventCode(): String
    fun updateEvent(eventForm: EventForm): Event

    fun getEventViewModelByCode(code: String): EventViewModel
    fun getEventByCode(code: String): Event?
    fun getEventEditViewModelByCode(code: String): EventEditViewModel

    fun updateLastUpdateToNow(event: Event)
}