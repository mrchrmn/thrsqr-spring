package dev.hrmn.thrsqrspring.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Entity
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long = 0,

    val code: String,
    val title: String,
    val dayOfWeek: Int,
    val eventTime: LocalTime,
    val timeZone: String,
    val info: String? = null,
    val lastUpdate: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC),
    val logoURL: String? = null
)
