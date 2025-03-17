package dev.hrmn.thrsqrspring.application.port.output

import dev.hrmn.thrsqrspring.domain.model.EventSubscription
import org.springframework.data.jpa.repository.JpaRepository

interface EventSubscriptionJpaRepository : JpaRepository<EventSubscription, Long> {
    fun existsByEventCodeAndSubscriptionEndpoint(eventCode: String, endpoint: String): Boolean

    fun deleteByEventIdAndSubscriptionEndpoint(eventId: Long, endpoint: String)

    fun deleteBySubscriptionEndpoint(endpoint: String)
}