package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.adapter.output.persistence.jpa.JpaTimezoneRepository
import dev.hrmn.thrsqrspring.application.port.output.TimezoneRepository
import dev.hrmn.thrsqrspring.domain.model.Timezone

class TimezoneRepository(private val jpaTimezoneRepository: JpaTimezoneRepository) : TimezoneRepository {
    override fun findByName(name: String): Timezone? {
        return jpaTimezoneRepository.findByName(name)
    }
}