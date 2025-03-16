package dev.hrmn.thrsqrspring.domain.model

import jakarta.persistence.*

@Entity
data class Response(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long = 0,

    @ManyToOne
    val event: Event,

    @ManyToOne
    val participant: Participant,

    var there: Boolean,
    var comment: String?
)
