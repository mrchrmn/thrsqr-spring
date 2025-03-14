package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.adapter.input.web.dto.TimezoneRequest
import dev.hrmn.thrsqrspring.application.port.input.TimezoneController
import dev.hrmn.thrsqrspring.application.service.TimeService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class TimezoneController(
    private val timeService: TimeService,
) : TimezoneController {
    @PostMapping("/timezone-abbrev")
    override fun getTimezoneAbbreviation(@RequestBody timezoneRequest: TimezoneRequest): ResponseEntity<String> {
        val abbreviation = timeService.getTimezoneAbbreviation(timezoneRequest.timezone)
        return ResponseEntity.ok(abbreviation)
    }
}