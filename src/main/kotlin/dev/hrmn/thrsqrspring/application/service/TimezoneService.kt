package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.output.persistence.TimezoneRepository
import dev.hrmn.thrsqrspring.application.port.input.TimezoneService
import org.springframework.stereotype.Service

@Service
class TimezoneService(
    private val timezoneRepository: TimezoneRepository
) : TimezoneService {
    private val timezoneAbbreviationCache = mutableMapOf<String, String>()

    override fun getTimezoneAbbreviation(name: String): String {
        return timezoneAbbreviationCache.getOrPut(name) {
            val timezone = timezoneRepository.findByName(name)
            timezone?.abbrev ?: ""
        }
    }
}
