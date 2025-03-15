package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.domain.model.Timezone

interface TimezoneService {
    fun getTimezoneAbbreviation(name: String): String
    fun getTimezoneByName(name: String): Timezone
}
