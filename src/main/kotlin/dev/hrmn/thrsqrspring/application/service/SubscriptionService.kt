package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.output.persistence.EventJpaAdapter
import dev.hrmn.thrsqrspring.adapter.output.persistence.SubscriptionJpaAdapter
import dev.hrmn.thrsqrspring.application.port.input.SubscriptionService
import dev.hrmn.thrsqrspring.domain.model.Subscription
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubscriptionService(
    private val subscriptionJpaAdapter: SubscriptionJpaAdapter,
    private val eventJpaAdapter: EventJpaAdapter
) : SubscriptionService {

    override fun isSubscribed(eventCode: String, subscription: Subscription): Boolean {
        return subscriptionJpaAdapter.isSubscribed(eventCode, subscription.endpoint)
    }

    @Transactional
    override fun subscribe(eventCode: String, subscription: Subscription) {
        val event = eventJpaAdapter.findByCode(eventCode)
            ?: throw IllegalArgumentException("Event not found with code: ${eventCode}")

        val savedSubscription =
            subscriptionJpaAdapter.findByEndpoint(subscription.endpoint) ?: subscriptionJpaAdapter.save(subscription)

        if (!isSubscribed(eventCode, subscription)) {
            subscriptionJpaAdapter.saveEventSubscription(event, savedSubscription)
        }
    }

    @Transactional
    override fun unsubscribe(eventCode: String, subscription: Subscription) {
        val event = eventJpaAdapter.findByCode(eventCode)
            ?: throw IllegalArgumentException("Event not found with code: ${eventCode}")

        val existingSubscription = subscriptionJpaAdapter.findByEndpoint(subscription.endpoint) ?: return

        subscriptionJpaAdapter.deleteEventSubscription(event, existingSubscription)
    }

    @Transactional
    override fun unsubscribeAll(subscription: Subscription) {
        val existingSubscription = subscriptionJpaAdapter.findByEndpoint(subscription.endpoint) ?: return

        subscriptionJpaAdapter.deleteAllEventSubscriptions(existingSubscription)
    }
}
