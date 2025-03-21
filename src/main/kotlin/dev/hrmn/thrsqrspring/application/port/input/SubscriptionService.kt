package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.domain.model.Subscription

interface SubscriptionService {
    fun isSubscribed(eventCode: String, subscription: Subscription): Boolean

    fun subscribe(eventCode: String, subscription: Subscription)
    fun unsubscribe(eventCode: String, subscription: Subscription)
    fun unsubscribeAll(subscription: Subscription)
}