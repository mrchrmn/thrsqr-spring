package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.application.port.input.SubscriptionController
import dev.hrmn.thrsqrspring.application.service.SubscriptionService
import dev.hrmn.thrsqrspring.domain.model.Subscription
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SubscriptionController(private val subscriptionService: SubscriptionService) : SubscriptionController {

    @PostMapping("/check-sub/{eventCode}")
    override fun checkSubscription(
        @PathVariable eventCode: String,
        @RequestBody subscription: Subscription
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(subscriptionService.isSubscribed(eventCode, subscription))
    }

    @PostMapping("/subscribe/{eventCode}")
    override fun subscribe(
        @PathVariable eventCode: String,
        @RequestBody subscription: Subscription
    ): ResponseEntity<Void> {
        subscriptionService.subscribe(eventCode, subscription)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/unsubscribe/{eventCode}")
    override fun unsubscribe(
        @PathVariable eventCode: String,
        @RequestBody subscription: Subscription
    ): ResponseEntity<Void> {
        subscriptionService.unsubscribe(eventCode, subscription)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/unsubscribe-all")
    override fun unsubscribeAll(@RequestBody subscription: Subscription): ResponseEntity<Void> {
        subscriptionService.unsubscribeAll(subscription)
        return ResponseEntity.ok().build()
    }
}