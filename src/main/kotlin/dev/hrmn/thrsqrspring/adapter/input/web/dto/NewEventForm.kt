package dev.hrmn.thrsqrspring.adapter.input.web.dto

data class NewEventForm(
    val eventTitle: String,
    val eventDayOfWeek: Int,
    val eventTime: String,
    val eventTimeZone: String,
    val eventInfo: String?,
    val eventLogoURL: String,
    val email: String,
    val message: String
)
