package dev.hrmn.thrsqrspring.domain.dto

data class ResponseDto(
    val participantId: Long,
    val there: Boolean,
    val username: String,
    val comment: String?
)
