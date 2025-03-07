package dev.hrmn.thrsqrspring.application.port.input

interface TimezoneService {
    fun getTimezoneAbbreviation(name: String): String
}
