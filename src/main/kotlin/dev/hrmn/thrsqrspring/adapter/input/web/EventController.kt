package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventForm
import dev.hrmn.thrsqrspring.application.port.input.EventController
import dev.hrmn.thrsqrspring.application.service.EventService
import dev.hrmn.thrsqrspring.domain.service.WebmanifestDomainService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/event", "/e")
class EventController(
    val eventService: EventService,
    private val webmanifestDomainService: WebmanifestDomainService,
) : EventController {

    @GetMapping("/new")
    override fun displayNewEventForm(model: Model): String {
        val newEventCode = eventService.generateEventCode()

        model.addAttribute("eventCode", newEventCode)

        return "new-event"
    }

    @PostMapping("/new")
    override fun createNewEvent(@ModelAttribute eventForm: EventForm, model: Model): String {
        // Honeypot: disregard form if invisible fields email or message are filled.
        if (!eventForm.email.isNullOrBlank() || !eventForm.message.isNullOrBlank()) {
            return "redirect:/"
        }

        val newEvent = eventService.createEvent(eventForm)

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
    override fun updateEvent(eventForm: EventForm): String {
        eventService.updateEvent(eventForm)

        return "redirect:/event/${eventForm.eventCode}"
    }

    @GetMapping("/{eventCode}/webmanifest")
    override fun sendWebManifest(@PathVariable eventCode: String, model: Model): ResponseEntity<String> {
        val event = eventService.getEventByCode(eventCode)
        val webmanifest = webmanifestDomainService.generateManifest(event)

        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_TYPE, "application/manifest+json")
            .body(webmanifest)
    }
}
