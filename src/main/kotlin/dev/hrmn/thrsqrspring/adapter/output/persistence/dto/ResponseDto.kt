package dev.hrmn.thrsqrspring.adapter.output.persistence.dto

data class ResponseDto(
    val id: Long,
    val there: Boolean,
    var username: String,
    val comment: String?
)
