package dev.hrmn.thrsqrspring.application.service

import com.fasterxml.jackson.databind.ObjectMapper
import dev.hrmn.thrsqrspring.adapter.input.web.dto.NewEventForm
import dev.hrmn.thrsqrspring.adapter.output.persistence.EventRepository
import dev.hrmn.thrsqrspring.adapter.output.persistence.ResponseRepository
import dev.hrmn.thrsqrspring.adapter.output.persistence.TimezoneRepository
import dev.hrmn.thrsqrspring.application.port.input.EventService
import dev.hrmn.thrsqrspring.application.port.input.dto.EventInfoDto
import dev.hrmn.thrsqrspring.domain.dto.ResponseDto
import dev.hrmn.thrsqrspring.domain.model.Event
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils.capitalize
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val responseRepository: ResponseRepository,
    private val timezoneRepository: TimezoneRepository
) : EventService {
    companion object {
        const val WAIT_TIME_IN_MINUTES: Long = 1 * 60
        const val DEFAULT_LOGO_URL = "/images/thrsqrlogo-250.png"
        val BUCKET = System.getenv("S3_BUCKET_NAME")
    }

    override fun createNewEvent(newEventForm: NewEventForm): Event {
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

        return eventRepository.save(event)
    }

    override fun getEventInfoByEventCode(code: String): EventInfoDto {
        val event = eventRepository.findByCode(code) ?: throw IllegalArgumentException("Requested event not found")

        resetResponsesIfOutdated(event)

        val responses = responseRepository.findByEvent(event)
        capitaliseUsernames(responses)

        val icons = getIcons(event)

        val logoURL = getLogoUrl(event)

        val going = responses.count { it.there }
        val notGoing = responses.count { !it.there }

        return EventInfoDto(
            event, responses, icons, logoURL, getPreviousEventTime(event), going, notGoing
        )
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

    private fun resetResponsesIfOutdated(event: Event) {
        val previousEventTime = getPreviousEventTime(event)
        val lastUpdate = event.lastUpdate
        val now = OffsetDateTime.now(ZoneOffset.UTC)

        if (now.isAfter(previousEventTime.plusMinutes(WAIT_TIME_IN_MINUTES)) &&
                lastUpdate.isBefore(previousEventTime.plusMinutes(WAIT_TIME_IN_MINUTES))) {
            responseRepository.deleteByEvent(event)
            eventRepository.updateLastUpdateToNow(event)
        }
    }

    private fun getPreviousEventTime(event: Event): OffsetDateTime {
        val eventTimezone = timezoneRepository.findByName(event.timeZone)
            ?: throw IllegalArgumentException("Requested time zone not found.")

        val offsetHours = eventTimezone.utcOffset.inWholeHours.toInt()
        val offSetMinutes = (eventTimezone.utcOffset.inWholeMinutes % 60).toInt()

        var eventHours = event.eventTime.hour - offsetHours
        val eventMinutes = event.eventTime.minute - offSetMinutes

        val eventDay = if (eventHours < 0) event.dayOfWeek - 1 else event.dayOfWeek
        eventHours = (24 + eventHours) % 24

        val now = OffsetDateTime.now(ZoneOffset.UTC)

        val nowDay = now.dayOfWeek.value
        val nowHours = now.hour
        val nowMinutes = now.minute

        var dateDelta = (nowDay - eventDay +7) % 7

        if (dateDelta == 0) {
            if (nowHours < eventHours || (nowHours == eventHours && nowMinutes < eventMinutes)) {
                dateDelta = 7
            }
        }

        val date = now
            .minusDays(dateDelta.toLong())
            .withHour(eventHours)
            .withMinute(eventMinutes)
            .truncatedTo(ChronoUnit.MINUTES)

        return date
    }

    private fun capitaliseUsernames(responses: List<ResponseDto>) {
        responses.forEach { it.username = capitalize(it.username) }
    }

    private fun getIcons(event: Event): Map<Int, String> {
        val logoURL = getLogoUrl(event)

        val sizes = listOf(144,192,256,512)
        val icons = sizes.associateWith { DEFAULT_LOGO_URL }.toMutableMap()

        if (logoURL.startsWith("https")) {
            icons.keys.forEach { key ->
                icons[key] = getResizedLogoURL(BUCKET, event.code, key)
            }
        }

        return icons
    }

    private fun getLogoUrl(event: Event): String {
        val logoURL = event.logoURL

        return if (!logoURL.isNullOrBlank() && logoURL.startsWith("https")) {
            getResizedLogoURL(BUCKET, event.code, 500)
        } else {
            DEFAULT_LOGO_URL
        }
    }

    private fun getResizedLogoURL(bucket: String, code: String, size: Int): String {
        val reqBody = mapOf(
            "bucket" to bucket,
            "key" to "logos/$code-logo",
            "edits" to mapOf(
                "resize" to mapOf(
                    "width" to size,
                    "height" to size,
                    "fit" to "cover"
                )
            )
        )

        val json = ObjectMapper().writeValueAsString(reqBody)
        val base64 = java.util.Base64.getEncoder().encodeToString(json.toByteArray())

        return "https://d2x3ofzbg5tm3i.cloudfront.net/$base64"
    }
}