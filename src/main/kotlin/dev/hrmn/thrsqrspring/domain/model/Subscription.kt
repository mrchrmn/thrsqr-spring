package dev.hrmn.thrsqrspring.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Subscription(
    @Id
    val endpoint: String,

    val expirationTime: Int?,
    val p256dh: String?,
    val auth: String?,
    val language: String?
)
