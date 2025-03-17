package dev.hrmn.thrsqrspring.application.port.output

import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Subscription

interface SubscriptionRepository {
    fun findByEndpoint(endpoint: String): Subscription?
    fun save(subscription: Subscription): Subscription
    fun isSubscribed(eventCode: String, endpoint: String): Boolean
    fun saveEventSubscription(event: Event, subscription: Subscription)
    fun deleteEventSubscription(event: Event, subscription: Subscription)
    fun deleteAllEventSubscriptions(subscription: Subscription)
    fun findSubscriptionsByEventCode(eventCode: String): List<Subscription>
}