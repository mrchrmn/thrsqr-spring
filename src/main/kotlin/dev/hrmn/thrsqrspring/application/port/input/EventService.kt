package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.NewEventForm

interface EventService {
    fun createNewEvent(newEventForm: NewEventForm)
}