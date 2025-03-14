package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.domain.model.Event
import java.time.OffsetDateTime

interface TimeService {
    fun getTimezoneAbbreviation(name: String): String
    fun getPreviousEventTime(event: Event): OffsetDateTime
}
