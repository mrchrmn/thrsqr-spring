package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.adapter.input.web.dto.SubscriptionRequest
import dev.hrmn.thrsqrspring.application.port.input.SubscriptionController
import dev.hrmn.thrsqrspring.application.service.SubscriptionService
import dev.hrmn.thrsqrspring.domain.model.Subscription
import org.springframework.context.i18n.LocaleContextHolder
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
        @RequestBody request: SubscriptionRequest
    ): ResponseEntity<Boolean> {
        val subscription = mapRequestToSubscription(request)
        return ResponseEntity.ok(subscriptionService.isSubscribed(eventCode, subscription))
    }

    @PostMapping("/subscribe/{eventCode}")
    override fun subscribe(
        @PathVariable eventCode: String,
        @RequestBody request: SubscriptionRequest,
    ): ResponseEntity<Void> {
        val subscription = mapRequestToSubscription(request)
        subscriptionService.subscribe(eventCode, subscription)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/unsubscribe/{eventCode}")
    override fun unsubscribe(
        @PathVariable eventCode: String,
        @RequestBody request: SubscriptionRequest
    ): ResponseEntity<Void> {
        val subscription = mapRequestToSubscription(request)
        subscriptionService.unsubscribe(eventCode, subscription)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/unsubscribe-all")
    override fun unsubscribeAll(@RequestBody request: SubscriptionRequest): ResponseEntity<Void> {
        val subscription = mapRequestToSubscription(request)
        subscriptionService.unsubscribeAll(subscription)
        return ResponseEntity.ok().build()
    }

    private fun mapRequestToSubscription(request: SubscriptionRequest): Subscription {
        return Subscription(
            endpoint = request.endpoint,
            expirationTime = request.expirationTime,
            p256dh = request.keys.p256dh,
            auth = request.keys.auth,
            language = request.language ?: LocaleContextHolder.getLocale().language
        )
    }
}