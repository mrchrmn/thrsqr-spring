package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.adapter.input.web.dto.NewEventForm
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
    override fun createNewEvent(@ModelAttribute newEventForm: NewEventForm, model: Model): String {
        // Honeypot: disregard form if invisible fields email or message are filled.
        if (newEventForm.email.isNotBlank() || newEventForm.message.isNotBlank()) {
            return "redirect:/"
        }

        val newEvent = eventService.createNewEvent(newEventForm)

        model.addAttribute("eventTitle", newEvent.title)
        model.addAttribute("eventCode", newEvent.code)

        return "new-event-success"
    }

    @GetMapping("/{eventCode}")
    override fun displayEvent(@PathVariable eventCode: String, model: Model, session: HttpSession): String {
        session.attributeNames.toList().forEach { name ->
            println("$name: ${session.getAttribute(name)}")
        }

        val eventViewModel = eventService.getEventViewModelByEventCode(eventCode)
        val username = session.getAttribute("username")

        model.addAttribute("event", eventViewModel.event)
        model.addAttribute("responses", eventViewModel.responses)
        model.addAttribute("icons", eventViewModel.icons)
        model.addAttribute("previousEventTime", eventViewModel.previousEventTime)
        model.addAttribute("going", eventViewModel.going)
        model.addAttribute("notGoing", eventViewModel.notGoing)
        model.addAttribute("username", username)

        return "event"
    }
}
