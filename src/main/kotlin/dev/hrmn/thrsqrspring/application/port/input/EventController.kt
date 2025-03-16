package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventForm
import jakarta.servlet.http.HttpSession
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable

interface EventController {
    fun displayEvent(@PathVariable eventCode: String, model: Model, session: HttpSession): String

    fun displayNewEventForm(model: Model): String
    fun createNewEvent(@ModelAttribute eventForm: EventForm, model: Model): String

    fun displayEventEditForm(@PathVariable eventCode: String, model: Model): String
    fun updateEvent(@PathVariable eventCode: String, @ModelAttribute eventForm: EventForm): String
}