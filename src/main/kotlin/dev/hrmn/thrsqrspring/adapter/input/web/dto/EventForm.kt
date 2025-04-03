package dev.hrmn.thrsqrspring.adapter.input.web.dto

data class EventForm(
    val eventTitle: String,
    val eventDayOfWeek: Int,
    val eventTime: String,
    val eventTimeZone: String,
    val eventInfo: String?,
    val eventLogoURL: String,
    val eventCode: String,
    val email: String?,
    val message: String?
)
