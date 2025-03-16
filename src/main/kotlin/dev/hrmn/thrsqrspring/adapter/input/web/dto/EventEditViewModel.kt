package dev.hrmn.thrsqrspring.adapter.input.web.dto

import dev.hrmn.thrsqrspring.domain.model.Event

data class EventEditViewModel(
    val event: Event,
    val logoURL: String,
)
