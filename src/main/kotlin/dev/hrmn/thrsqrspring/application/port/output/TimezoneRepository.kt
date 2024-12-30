package dev.hrmn.thrsqrspring.application.port.output

import dev.hrmn.thrsqrspring.domain.model.Timezone

interface TimezoneRepository {
    fun findByName(name: String): Timezone?
}