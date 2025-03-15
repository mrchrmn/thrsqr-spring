package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.application.port.output.TimezoneJpaRepository
import dev.hrmn.thrsqrspring.application.port.output.TimezoneRepository
import dev.hrmn.thrsqrspring.domain.model.Timezone
import org.springframework.stereotype.Repository

@Repository
class TimezoneJpaAdapter(private val timezoneJpaRepository: TimezoneJpaRepository) : TimezoneRepository {
    override fun findByName(name: String): Timezone? {
        return timezoneJpaRepository.findByName(name)
    }
}