package dev.hrmn.thrsqrspring.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
data class EventSubscription(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,

    @ManyToOne
    val event: Event,

    @ManyToOne
    val subscription: Subscription
)
