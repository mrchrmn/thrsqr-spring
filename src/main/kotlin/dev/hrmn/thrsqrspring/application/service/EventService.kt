package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventEditViewModel
import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventForm
import dev.hrmn.thrsqrspring.adapter.input.web.dto.EventViewModel
import dev.hrmn.thrsqrspring.adapter.output.persistence.EventJpaAdapter
import dev.hrmn.thrsqrspring.application.port.input.EventService
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.service.EventDomainService
import dev.hrmn.thrsqrspring.domain.service.LogoDomainService
import dev.hrmn.thrsqrspring.domain.service.ResponseDomainService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime
import java.time.OffsetDateTime

@Service
class EventService(
    private val eventJpaAdapter: EventJpaAdapter,
    private val responseService: ResponseService,
    private val timezoneService: TimezoneService,
    private val eventDomainService: EventDomainService,
    private val responseDomainService: ResponseDomainService,
    private val logoDomainService: LogoDomainService
) : EventService {

    @Transactional
    override fun createOrUpdateEvent(eventForm: EventForm, code: String?): Event {
        return if (code == null) {
            val newCode = eventDomainService.generateEventCode { generatedCode ->
                eventJpaAdapter.findByCode(generatedCode) == null
            }

            Event(
                code = newCode,
                title = eventForm.eventTitle,
                dayOfWeek = eventForm.eventDayOfWeek,
                eventTime = LocalTime.parse(eventForm.eventTime),
                timeZone = eventForm.eventTimeZone,
                info = eventForm.eventInfo,
                logoURL = eventForm.eventLogoURL
            )
        } else {
            val existingEvent = eventJpaAdapter.findByCode(code)
                ?: throw EntityNotFoundException("Event with code $code not found")

            existingEvent.apply {
                title = eventForm.eventTitle
                dayOfWeek = eventForm.eventDayOfWeek
                eventTime = LocalTime.parse(eventForm.eventTime)
                timeZone = eventForm.eventTimeZone
                info = eventForm.eventInfo
                logoURL = eventForm.eventLogoURL
            }
        }.let { eventJpaAdapter.save(it) }
    }

    @Transactional
    override fun getEventViewModelByCode(code: String): EventViewModel {
        val event = eventJpaAdapter.findByCode(code) ?: throw IllegalArgumentException("Requested event not found")

        val timezone = timezoneService.getTimezoneByName(event.timeZone)
        val previousEventTime = eventDomainService.getPreviousEventTime(event, timezone)

        resetResponsesIfOutdated(event, previousEventTime)
        val responses = responseService.findByEvent(event)
        responseDomainService.capitaliseUsernames(responses)

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
            responseService.deleteAllFromEvent(event)
            eventJpaAdapter.updateLastUpdateToNow(event)
        }
    }
}