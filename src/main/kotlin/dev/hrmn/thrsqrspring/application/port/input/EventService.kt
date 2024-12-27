package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.NewEventForm
import dev.hrmn.thrsqrspring.domain.model.Event

interface EventService {
    fun createNewEvent(newEventForm: NewEventForm): Event
}