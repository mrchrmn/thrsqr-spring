package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.input.web.dto.NewEventForm
import dev.hrmn.thrsqrspring.adapter.output.EventRepository
import dev.hrmn.thrsqrspring.application.port.input.EventService
import dev.hrmn.thrsqrspring.domain.model.Event
import java.time.LocalTime

class EventService(private val eventRepository: EventRepository) : EventService {
    override fun createNewEvent(newEventForm: NewEventForm) {
        val code = generateEventCode()
        val event = Event(
            code = code,
            title = newEventForm.eventTitle,
            dayOfWeek = newEventForm.eventDayOfWeek,
            eventTime = LocalTime.parse(newEventForm.eventTime),
            timeZone = newEventForm.eventTimeZone,
            info = newEventForm.eventInfo,
            logoURL = newEventForm.eventLogoURL
        )

        eventRepository.save(event)
    }

    private fun generateEventCode(): String {
        val alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return generateSequence {
            buildString {
                repeat(4) {
                    append(alphabet.random())
                }
            }
        }.first { eventRepository.findByCode(it) == null }
    }

}