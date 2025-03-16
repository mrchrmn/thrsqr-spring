package dev.hrmn.thrsqrspring.adapter.input.web.dto

data class DeleteResponseForm(
    val responseIdToDelete: Long,
    val eventCode: String,
)
