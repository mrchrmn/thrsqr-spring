package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.NewEventForm
import org.springframework.ui.Model

interface EventController {
    fun displayNewEventForm(model: Model): String
    fun createNewEvent(newEventForm: NewEventForm, model: Model): String
}