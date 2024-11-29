package dev.hrmn.thrsqrspring.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class Response(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,

    @ManyToOne
    val event: Event,

    @ManyToOne
    val participant: Participant,

    val there: Boolean,
    val comment: String?
)
