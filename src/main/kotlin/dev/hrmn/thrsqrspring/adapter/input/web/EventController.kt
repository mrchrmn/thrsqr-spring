package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.adapter.input.web.dto.NewEventForm
import dev.hrmn.thrsqrspring.application.port.input.EventController
import dev.hrmn.thrsqrspring.application.service.EventService
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
    override fun displayEvent(@PathVariable eventCode: String, model: Model): String {
        val eventInfo = eventService.getEventInfoByEventCode(eventCode)

        model.addAttribute("event", eventInfo.event)
        model.addAttribute("responses", eventInfo.responses)
        model.addAttribute("icons", eventInfo.icons)
        model.addAttribute("previousEventTime", eventInfo.previousEventTime)
        model.addAttribute("going", eventInfo.going)
        model.addAttribute("notGoing", eventInfo.notGoing)

        return "event"
    }
}
