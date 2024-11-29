package dev.hrmn.thrsqrspring.domain.model

import jakarta.persistence.*

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
