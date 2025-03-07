package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.TimezoneRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

interface TimezoneController {
    fun getTimezoneAbbreviation(@RequestBody timezoneRequest: TimezoneRequest): ResponseEntity<String>
}