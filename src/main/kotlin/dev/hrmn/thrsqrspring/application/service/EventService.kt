package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventViewModel
import dev.hrmn.thrsqrspring.adapter.input.web.dto.NewEventForm
import dev.hrmn.thrsqrspring.adapter.output.persistence.EventRepository
import dev.hrmn.thrsqrspring.application.port.input.EventService
import dev.hrmn.thrsqrspring.application.util.EventUtils
import dev.hrmn.thrsqrspring.domain.model.Event
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val responseService: ResponseService,
    private val timeService: TimeService,
) : EventService {
    companion object {
        const val WAIT_TIME_IN_MINUTES: Long = 1 * 60
    }

    override fun createNewEvent(newEventForm: NewEventForm): Event {
        val code = EventUtils.generateEventCode(eventRepository)
        val event = Event(
            code = code,
            title = newEventForm.eventTitle,
            dayOfWeek = newEventForm.eventDayOfWeek,
            eventTime = LocalTime.parse(newEventForm.eventTime),
            timeZone = newEventForm.eventTimeZone,
            info = newEventForm.eventInfo,
            logoURL = newEventForm.eventLogoURL
        )

        return eventRepository.save(event)
    }

    @Transactional
    override fun getEventInfoByEventCode(code: String): EventViewModel {
        val event = eventRepository.findByCode(code) ?: throw IllegalArgumentException("Requested event not found")

        resetResponsesIfOutdated(event)
        val responses = responseService.getResponsesByEvent(event)
        EventUtils.capitaliseUsernames(responses)

        val icons = EventUtils.getIcons(event)
        val logoURL = EventUtils.getLogoUrl(event)
        val previousEventTime = timeService.getPreviousEventTime(event)
        val going = responses.count { it.there }
        val notGoing = responses.count { !it.there }

        return EventViewModel(
            event, responses, icons, logoURL, previousEventTime, going, notGoing
        )
    }

    private fun resetResponsesIfOutdated(event: Event) {
        val previousEventTime = timeService.getPreviousEventTime(event)
        val lastUpdate = event.lastUpdate
        val now = OffsetDateTime.now(ZoneOffset.UTC)

        if (now.isAfter(previousEventTime.plusMinutes(WAIT_TIME_IN_MINUTES)) &&
            lastUpdate.isBefore(previousEventTime.plusMinutes(WAIT_TIME_IN_MINUTES))
        ) {
            responseService.deleteAllResponsesFromEvent(event)
            eventRepository.updateLastUpdateToNow(event)
        }
    }
}