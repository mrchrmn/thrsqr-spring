package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.domain.model.Subscription
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface SubscriptionController {
    fun checkSubscription(
        @PathVariable eventCode: String,
        @RequestBody subscription: Subscription
    ): ResponseEntity<Boolean>

    fun subscribe(@PathVariable eventCode: String, @RequestBody subscription: Subscription): ResponseEntity<Void>
    fun unsubscribe(@PathVariable eventCode: String, @RequestBody subscription: Subscription): ResponseEntity<Void>
    fun unsubscribeAll(@RequestBody subscription: Subscription): ResponseEntity<Void>
}