package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventEditViewModel
import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventForm
import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventViewModel
import dev.hrmn.thrsqrspring.adapter.output.persistence.EventJpaAdapter
import dev.hrmn.thrsqrspring.adapter.output.persistence.ResponseJpaAdapter
import dev.hrmn.thrsqrspring.adapter.output.persistence.TimezoneJpaAdapter
import dev.hrmn.thrsqrspring.application.port.input.EventService
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.service.EventDomainService
import dev.hrmn.thrsqrspring.domain.service.LogoDomainService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime
import java.time.OffsetDateTime

@Service
class EventService(
    private val eventJpaAdapter: EventJpaAdapter,
    private val responseJpaAdapter: ResponseJpaAdapter,
    private val timezoneJpaAdapter: TimezoneJpaAdapter,
    private val eventDomainService: EventDomainService,
    private val logoDomainService: LogoDomainService
) : EventService {

    @Transactional
    override fun createEvent(eventForm: EventForm): Event {
        return Event(
            code = eventForm.eventCode,
            title = eventForm.eventTitle,
            dayOfWeek = eventForm.eventDayOfWeek,
            eventTime = LocalTime.parse(eventForm.eventTime),
            timeZone = eventForm.eventTimeZone,
            info = eventForm.eventInfo,
            logoURL = eventForm.eventLogoURL
        ).let { eventJpaAdapter.save(it) }
    }

    override fun updateEvent(eventForm: EventForm): Event {
        val code = eventForm.eventCode
        val existingEvent = eventJpaAdapter.findByCode(code)
            ?: throw EntityNotFoundException("Event with code $code not found")

        return existingEvent.apply {
            title = eventForm.eventTitle
            dayOfWeek = eventForm.eventDayOfWeek
            eventTime = LocalTime.parse(eventForm.eventTime)
            timeZone = eventForm.eventTimeZone
            info = eventForm.eventInfo
            logoURL = eventForm.eventLogoURL
        }.let { eventJpaAdapter.save(it) }
    }

    override fun generateEventCode(): String {
        return eventDomainService.generateEventCode { generatedCode ->
            eventJpaAdapter.findByCode(generatedCode) == null
        }
    }

    @Transactional
    override fun getEventViewModelByCode(code: String): EventViewModel {
        val event = eventJpaAdapter.findByCode(code) ?: throw IllegalArgumentException("Requested event not found")

        val timezone = timezoneJpaAdapter.findByName(event.timeZone)
            ?: throw IllegalArgumentException("Requested time zone not found.")
        val previousEventTime = eventDomainService.getPreviousEventTime(event, timezone)

        resetResponsesIfOutdated(event, previousEventTime)
        val responses = responseJpaAdapter.findDtoByEvent(event)

        val icons = logoDomainService.getIcons(event)
        val logoURL = logoDomainService.getLogoUrl(event)
        val going = responses.count { it.there }
        val notGoing = responses.count { !it.there }

        return EventViewModel(
            event, responses, icons, logoURL, previousEventTime, going, notGoing
        )
    }

    override fun getEventEditViewModelByCode(code: String): EventEditViewModel {
        val event = eventJpaAdapter.findByCode(code) ?: throw IllegalArgumentException("Requested event not found")
        val logoURL = logoDomainService.getLogoUrl(event)

        return EventEditViewModel(
            event, logoURL
        )
    }

    override fun getEventByCode(code: String): Event {
        return eventJpaAdapter.findByCode(code)
            ?: throw IllegalArgumentException("Event not found with code: ${code}")
    }

    @Transactional
    override fun updateLastUpdateToNow(event: Event) {
        eventJpaAdapter.updateLastUpdateToNow(event)
    }


    private fun resetResponsesIfOutdated(event: Event, previousEventTime: OffsetDateTime) {
        if (eventDomainService.shouldResetResponses(event, previousEventTime)) {
            responseJpaAdapter.deleteByEvent(event)
            eventJpaAdapter.updateLastUpdateToNow(event)
        }
    }
}
