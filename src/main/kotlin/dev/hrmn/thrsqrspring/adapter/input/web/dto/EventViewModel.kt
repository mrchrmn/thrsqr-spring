package dev.hrmn.thrsqrspring.adapter.input.web.dto

import dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto
import dev.hrmn.thrsqrspring.domain.model.Event
import java.time.OffsetDateTime

data class EventViewModel(
    val event: Event,
    val responses: List<ResponseDto>,
    val icons: Map<Int, String>,
    val logoURL: String,
    val previousEventTime: OffsetDateTime,
    val going: Int,
    val notGoing: Int
)
