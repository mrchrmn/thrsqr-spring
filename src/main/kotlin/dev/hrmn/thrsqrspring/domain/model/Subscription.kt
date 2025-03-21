package dev.hrmn.thrsqrspring.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Subscription(
    @Id
    @Column(length = 1000) // Increased length to accommodate longer URLs
    val endpoint: String,

    val expirationTime: Int?,

    @Column(length = 500) // Also increase p256dh to be safe
    val p256dh: String?,

    @Column(length = 255)
    val auth: String?,

    val language: String?
)
