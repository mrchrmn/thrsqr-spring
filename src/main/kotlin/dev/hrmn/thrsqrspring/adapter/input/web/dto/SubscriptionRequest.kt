package dev.hrmn.thrsqrspring.adapter.input.web.dto

data class SubscriptionRequest(
    val endpoint: String,
    val expirationTime: Int?,
    val keys: SubscriptionKeys,
    val language: String? = null // Optional if you want to extract language from request
)

data class SubscriptionKeys(
    val p256dh: String,
    val auth: String
)
