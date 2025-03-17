package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.application.port.output.EventSubscriptionJpaRepository
import dev.hrmn.thrsqrspring.application.port.output.SubscriptionJpaRepository
import dev.hrmn.thrsqrspring.application.port.output.SubscriptionRepository
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.EventSubscription
import dev.hrmn.thrsqrspring.domain.model.Subscription
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class SubscriptionJpaAdapter(
    private val subscriptionJpaRepository: SubscriptionJpaRepository,
    private val eventSubscriptionJpaRepository: EventSubscriptionJpaRepository
) : SubscriptionRepository {

    override fun findByEndpoint(endpoint: String): Subscription? {
        return subscriptionJpaRepository.findById(endpoint).orElse(null)
    }

    override fun save(subscription: Subscription): Subscription {
        return subscriptionJpaRepository.save(subscription)
    }

    override fun isSubscribed(eventCode: String, endpoint: String): Boolean {
        return eventSubscriptionJpaRepository.existsByEventCodeAndSubscriptionEndpoint(eventCode, endpoint)
    }

    @Transactional
    override fun saveEventSubscription(event: Event, subscription: Subscription) {
        val eventSubscription = EventSubscription(0, event, subscription)
        eventSubscriptionJpaRepository.save(eventSubscription)
    }

    @Transactional
    override fun deleteEventSubscription(event: Event, subscription: Subscription) {
        eventSubscriptionJpaRepository.deleteByEventIdAndSubscriptionEndpoint(event.id, subscription.endpoint)
    }

    @Transactional
    override fun deleteAllEventSubscriptions(subscription: Subscription) {
        eventSubscriptionJpaRepository.deleteBySubscriptionEndpoint(subscription.endpoint)
    }

    override fun findSubscriptionsByEventCode(eventCode: String): List<Subscription> {
        return subscriptionJpaRepository.findAllByEventCode(eventCode)
    }
}