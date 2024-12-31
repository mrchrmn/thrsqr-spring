package dev.hrmn.thrsqrspring.application.port.input.dto

import dev.hrmn.thrsqrspring.domain.dto.ResponseDto
import dev.hrmn.thrsqrspring.domain.model.Event
import java.time.OffsetDateTime

data class EventInfoDto(
    val event: Event,
    val responses: List<ResponseDto>,
    val icons: Map<Int, String>,
    val previousEventTime: OffsetDateTime,
    val going: Int,
    val notGoing: Int
)
