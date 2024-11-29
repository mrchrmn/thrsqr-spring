package dev.hrmn.thrsqrspring.adapter.input.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/event")
class EventController {
    @GetMapping("/new")
    fun newEvent(model: Model): String {
        model.addAttribute("eventId", 1234)
        return "new-event"
    }
}
