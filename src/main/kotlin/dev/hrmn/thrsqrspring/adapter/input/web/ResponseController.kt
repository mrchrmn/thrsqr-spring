package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.adapter.input.web.dto.DeleteResponseForm
import dev.hrmn.thrsqrspring.adapter.input.web.dto.ResponseForm
import dev.hrmn.thrsqrspring.application.port.input.ResponseController
import dev.hrmn.thrsqrspring.application.service.EventService
import dev.hrmn.thrsqrspring.application.service.ParticipantService
import dev.hrmn.thrsqrspring.application.service.ResponseService
import dev.hrmn.thrsqrspring.domain.model.Response
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/response")
class ResponseController(
    private val participantService: ParticipantService,
    private val responseService: ResponseService,
    private val eventService: EventService,
) : ResponseController {
    @PostMapping("/")
    override fun save(@ModelAttribute responseForm: ResponseForm, session: HttpSession): String {
        val participantId = session.getAttribute("participantId") as? Long

        val participant = if (participantId == null) {
            participantService.createNewParticipant(responseForm.username).also {
                session.setAttribute("participantId", it.id)
            }
        } else {
            participantService.getParticipantById(participantId)
        }

        val event = eventService.getEventByEventCode(responseForm.eventCode)
            ?: throw IllegalArgumentException("Event not found with code: ${responseForm.eventCode}")

        val there = responseForm.there == "there"

        val newResponse =
            Response(event = event, comment = responseForm.comment, participant = participant, there = there)

        return "redirect:/event/${responseForm.eventCode}"
    }

    @PostMapping("/delete")
    override fun delete(@ModelAttribute deleteResponseForm: DeleteResponseForm, session: HttpSession): String {
        // TODO: Remove response from database

        return "redirect:/event/${deleteResponseForm.eventCode}"
    }
}
