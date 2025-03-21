package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.SubscriptionRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface SubscriptionController {
    fun checkSubscription(
        @PathVariable eventCode: String,
        @RequestBody request: SubscriptionRequest
    ): ResponseEntity<Boolean>

    fun subscribe(@PathVariable eventCode: String, @RequestBody request: SubscriptionRequest): ResponseEntity<Void>
    fun unsubscribe(@PathVariable eventCode: String, @RequestBody request: SubscriptionRequest): ResponseEntity<Void>
    fun unsubscribeAll(@RequestBody request: SubscriptionRequest): ResponseEntity<Void>
}