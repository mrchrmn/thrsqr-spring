package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.adapter.input.web.dto.DeleteResponseForm
import dev.hrmn.thrsqrspring.adapter.input.web.dto.ResponseForm
import dev.hrmn.thrsqrspring.application.port.input.ResponseController
import dev.hrmn.thrsqrspring.application.service.EventService
import dev.hrmn.thrsqrspring.application.service.ParticipantService
import dev.hrmn.thrsqrspring.application.service.ResponseService
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
        val username = responseForm.username.ifBlank { "Anonymous" }

        val participant = participantService.createOrUpdateParticipant(participantId, username).also {
            session.setAttribute("participantId", it.id)
            session.setAttribute("username", it.username)
        }

        val event = eventService.getEventByEventCode(responseForm.eventCode)

        responseService.createOrUpdateResponse(participant, event, responseForm.comment, responseForm.there)

        eventService.updateLastUpdateToNow(event)

        return "redirect:/event/${responseForm.eventCode}"
    }

    @PostMapping("/delete")
    override fun delete(@ModelAttribute deleteResponseForm: DeleteResponseForm, session: HttpSession): String {
        responseService.deleteById(deleteResponseForm.responseIdToDelete)

        return "redirect:/event/${deleteResponseForm.eventCode}"
    }
}
