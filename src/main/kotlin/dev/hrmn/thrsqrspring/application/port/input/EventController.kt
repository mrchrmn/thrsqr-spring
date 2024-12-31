package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.NewEventForm
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable

interface EventController {
    fun displayNewEventForm(model: Model): String
    fun createNewEvent(newEventForm: NewEventForm, model: Model): String
    fun displayEvent(@PathVariable eventCode: String, model: Model): String
}