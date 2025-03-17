package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.output.persistence.TimezoneJpaAdapter
import dev.hrmn.thrsqrspring.application.port.input.TimezoneService
import dev.hrmn.thrsqrspring.domain.model.Timezone
import org.springframework.stereotype.Service

@Service
class TimezoneService(
    private val timezoneJpaAdapter: TimezoneJpaAdapter
) : TimezoneService {

    private val timezoneAbbreviationCache = mutableMapOf<String, String>()

    override fun getTimezoneAbbreviation(name: String): String {
        return timezoneAbbreviationCache.getOrPut(name) {
            val timezone = timezoneJpaAdapter.findByName(name)
            timezone?.abbrev ?: ""
        }
    }

    override fun getTimezoneByName(name: String): Timezone {
        return timezoneJpaAdapter.findByName(name)
            ?: throw IllegalArgumentException("Requested time zone not found.")
    }
}
