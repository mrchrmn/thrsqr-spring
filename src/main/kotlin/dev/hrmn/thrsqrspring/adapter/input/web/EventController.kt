package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.adapter.input.web.dto.NewEventForm
import dev.hrmn.thrsqrspring.application.port.input.EventController
import dev.hrmn.thrsqrspring.application.service.EventService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/event")
class EventController(val eventService: EventService): EventController {
    @GetMapping("/new")
    override fun displayNewEventForm(model: Model): String {
        return "new-event"
    }

    @PostMapping("/new")
    override fun createNewEvent(@ModelAttribute newEventForm: NewEventForm, model: Model) :String {
        // Honeypot: disregard form if invisible fields email or message are filled.
        if (newEventForm.email.isNotBlank() || newEventForm.message.isNotBlank()) {
            return "redirect:/"
        }

        val newEvent = eventService.createNewEvent(newEventForm)

        model.addAttribute("eventTitle", newEvent.title)
        model.addAttribute("eventCode", newEvent.code)

        return "new-event-success"
    }
}
