package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.adapter.input.web.dto.RemoveResponseForm
import dev.hrmn.thrsqrspring.adapter.input.web.dto.ResponseForm
import dev.hrmn.thrsqrspring.application.port.input.ResponseController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/response")
class ResponseController() : ResponseController {
    @PostMapping("/")
    override fun save(@ModelAttribute responseForm: ResponseForm): String {
        // TODO: Save response to database

        return "redirect:/event/${responseForm.eventCode}"
    }

    @PostMapping("/remove")
    override fun remove(@ModelAttribute removeResponseForm: RemoveResponseForm): String {
        // TODO: Remove response from database

        return "redirect:/event/${removeResponseForm.eventCode}"
    }
}
