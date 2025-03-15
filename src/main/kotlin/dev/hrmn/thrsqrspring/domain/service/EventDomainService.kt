package dev.hrmn.thrsqrspring.domain.service

import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Timezone
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.parseIsoString

@Service
class EventDomainService {
    companion object {
        const val WAIT_TIME_IN_MINUTES: Long = 1 * 60
    }

    fun shouldResetResponses(event: Event, previousEventTime: OffsetDateTime): Boolean {
        val lastUpdate = event.lastUpdate
        val now = OffsetDateTime.now(ZoneOffset.UTC)
        val resetThreshold = previousEventTime.plusMinutes(WAIT_TIME_IN_MINUTES)

        return (now.isAfter(resetThreshold) &&
                lastUpdate.isBefore(resetThreshold)
                )
    }

    fun generateEventCode(isCodeUnique: (String) -> Boolean): String {
        val alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return generateSequence {
            buildString {
                repeat(4) {
                    append(alphabet.random())
                }
            }
        }.first(isCodeUnique)
    }

    fun getPreviousEventTime(event: Event, timezone: Timezone): OffsetDateTime {
        val utcOffset = parseDurationString(timezone.utcOffset)

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