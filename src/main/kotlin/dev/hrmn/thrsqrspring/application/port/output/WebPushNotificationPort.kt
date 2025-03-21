package dev.hrmn.thrsqrspring.application.port.output

import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Participant
import dev.hrmn.thrsqrspring.domain.model.Response
import dev.hrmn.thrsqrspring.domain.model.Subscription

interface WebPushNotificationPort {
    fun sendResponseUpdateNotification(
        event: Event,
        participant: Participant?,
        response: Response?,
        subscriptions: List<Subscription>,
        goingCount: Int,
        notGoingCount: Int
    )
}