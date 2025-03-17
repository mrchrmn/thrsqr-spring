package dev.hrmn.thrsqrspring.application.port.output

import dev.hrmn.thrsqrspring.domain.model.Subscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SubscriptionJpaRepository : JpaRepository<Subscription, String> {
    @Query(
        "SELECT s FROM Subscription s JOIN EventSubscription es ON s.endpoint = es.subscription.endpoint " +
                "JOIN Event e ON es.event.id = e.id WHERE e.code = :eventCode"
    )
    fun findAllByEventCode(eventCode: String): List<Subscription>
}