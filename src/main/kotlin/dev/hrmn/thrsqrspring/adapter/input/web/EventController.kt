package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventForm
import dev.hrmn.thrsqrspring.application.port.input.EventController
import dev.hrmn.thrsqrspring.application.service.EventService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/event", "/e")
class EventController(val eventService: EventService) : EventController {
    @GetMapping("/new")
    override fun displayNewEventForm(model: Model): String {
        return "new-event"
    }

    @PostMapping("/new")
    override fun createNewEvent(@ModelAttribute eventForm: EventForm, model: Model): String {
        // Honeypot: disregard form if invisible fields email or message are filled.
        if (!eventForm.email.isNullOrBlank() || !eventForm.message.isNullOrBlank()) {
            return "redirect:/"
        }

        val newEvent = eventService.createOrUpdateEvent(eventForm)

        model.addAttribute("eventTitle", newEvent.title)
        model.addAttribute("eventCode", newEvent.code)

        return "new-event-success"
    }

    @GetMapping("/{eventCode}")
    override fun displayEvent(@PathVariable eventCode: String, model: Model, session: HttpSession): String {
        val eventViewModel = eventService.getEventViewModelByCode(eventCode)
        val username = session.getAttribute("username")

        model.addAttribute("event", eventViewModel.event)
        model.addAttribute("responses", eventViewModel.responses)
        model.addAttribute("logoURL", eventViewModel.logoURL)
        model.addAttribute("icons", eventViewModel.icons)
        model.addAttribute("previousEventTime", eventViewModel.previousEventTime)
        model.addAttribute("going", eventViewModel.going)
        model.addAttribute("notGoing", eventViewModel.notGoing)
        model.addAttribute("username", username)

        return "event"
    }

    @GetMapping("/{eventCode}/edit")
    override fun displayEventEditForm(@PathVariable eventCode: String, model: Model): String {
        val eventEditViewModel = eventService.getEventEditViewModelByCode(eventCode)

        model.addAttribute("event", eventEditViewModel.event)
        model.addAttribute("logoURL", eventEditViewModel.logoURL)

        return "edit-event"
    }

    @PostMapping("/{eventCode}/edit")
    override fun updateEvent(@PathVariable eventCode: String, eventForm: EventForm): String {
        eventService.createOrUpdateEvent(eventForm, eventCode)

        return "redirect:/event/${eventCode}"
    }
}
