package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.output.persistence.TimezoneRepository
import dev.hrmn.thrsqrspring.application.port.input.TimezoneService
import org.springframework.stereotype.Service

@Service
class TimezoneService(
    private val timezoneRepository: TimezoneRepository
) : TimezoneService {
    override fun getTimezoneAbbreviation(name: String): String {
        val timezone = timezoneRepository.findByName(name);
        return timezone?.abbrev ?: ""
    }
}
