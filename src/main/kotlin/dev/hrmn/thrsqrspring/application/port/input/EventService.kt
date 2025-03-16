package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventEditViewModel
import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventForm
import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventViewModel
import dev.hrmn.thrsqrspring.domain.model.Event

interface EventService {
    fun createNewEvent(eventForm: EventForm): Event
    fun getEventViewModelByCode(code: String): EventViewModel
    fun getEventByCode(code: String): Event?
    fun updateLastUpdateToNow(event: Event)
    fun getEventEditViewModelByCode(code: String): EventEditViewModel
}