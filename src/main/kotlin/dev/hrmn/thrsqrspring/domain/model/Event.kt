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
    var title: String,
    var dayOfWeek: Int,
    var eventTime: LocalTime,
    var timeZone: String,
    var info: String? = null,
    val lastUpdate: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC),
    var logoURL: String? = null
)
