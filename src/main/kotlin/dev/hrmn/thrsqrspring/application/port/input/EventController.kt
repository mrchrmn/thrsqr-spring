package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventForm
import jakarta.servlet.http.HttpSession
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable

interface EventController {
    fun displayNewEventForm(model: Model): String
    fun createNewEvent(eventForm: EventForm, model: Model): String
    fun displayEvent(@PathVariable eventCode: String, model: Model, session: HttpSession): String
    fun updateEvent(@PathVariable eventCode: String, model: Model): String
}