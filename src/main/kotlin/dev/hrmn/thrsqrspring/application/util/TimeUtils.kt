package dev.hrmn.thrsqrspring.application.util

import dev.hrmn.thrsqrspring.adapter.output.persistence.TimezoneRepository
import dev.hrmn.thrsqrspring.domain.model.Event
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.parseIsoString

object TimeUtils {
    fun getPreviousEventTime(event: Event, timezoneRepository: TimezoneRepository): OffsetDateTime {
        val eventTimezone = timezoneRepository.findByName(event.timeZone)
            ?: throw IllegalArgumentException("Requested time zone not found.")

        val utcOffset = parseDurationString(eventTimezone.utcOffset)

        val offsetHours = utcOffset.inWholeHours.toInt()
        val offSetMinutes = (utcOffset.inWholeMinutes % 60).toInt()

        var eventHours = event.eventTime.hour - offsetHours
        val eventMinutes = event.eventTime.minute - offSetMinutes

        val eventDay = if (eventHours < 0) event.dayOfWeek - 1 else event.dayOfWeek
        eventHours = (24 + eventHours) % 24

        val now = OffsetDateTime.now(ZoneOffset.UTC)

        val nowDay = now.dayOfWeek.value
        val nowHours = now.hour
        val nowMinutes = now.minute

        var dateDelta = (nowDay - eventDay + 7) % 7

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

    private fun parseDurationString(durationString: String): Duration {
        val parts = durationString.split(":")
        val hours = parts[0].toLong()
        val minutes = parts[1].toLong()
        val seconds = parts[2].toLong()
        return parseIsoString("PT${hours}H${minutes}M${seconds}S")
    }
}