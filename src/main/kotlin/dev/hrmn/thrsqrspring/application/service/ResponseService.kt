package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.output.persistence.ResponseJpaAdapter
import dev.hrmn.thrsqrspring.adapter.output.persistence.SubscriptionJpaAdapter
import dev.hrmn.thrsqrspring.adapter.output.webpush.WebPushNotificationAdapter
import dev.hrmn.thrsqrspring.application.port.input.ResponseService
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Participant
import dev.hrmn.thrsqrspring.domain.model.Response
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResponseService(
    private val responseJpaAdapter: ResponseJpaAdapter,
    private val subscriptionJpaAdapter: SubscriptionJpaAdapter,
    private val webPushNotificationAdapter: WebPushNotificationAdapter
) : ResponseService {

    @Transactional
    override fun createOrUpdateResponse(
        participant: Participant,
        event: Event,
        comment: String,
        there: Boolean
    ): Response {
        val savedResponse = saveResponse(participant, event, comment, there)
        sendNotifications(event, participant, savedResponse)
        return savedResponse
    }

    override fun deleteById(id: Long) = responseJpaAdapter.deleteById(id)

    private fun saveResponse(
        participant: Participant,
        event: Event,
        comment: String,
        there: Boolean
    ): Response {
        val existingResponse = responseJpaAdapter.findByParticipantAndEvent(participant, event)

        return (existingResponse?.apply {
            this.comment = comment
            this.there = there
        }
            ?: Response(
                event = event,
                comment = comment,
                participant = participant,
                there = there
            )).let { responseJpaAdapter.save(it) }
    }

    private fun sendNotifications(event: Event, participant: Participant, savedResponse: Response) {
        val responses = responseJpaAdapter.findDtoByEvent(event)
        val goingCount = responses.count { it.there }
        val notGoingCount = responses.size - goingCount
        val subscriptions = subscriptionJpaAdapter.findSubscriptionsByEventCode(event.code)

        webPushNotificationAdapter.sendResponseUpdateNotification(
            event = event,
            participant = participant,
            response = savedResponse,
            subscriptions = subscriptions,
            goingCount = goingCount,
            notGoingCount = notGoingCount
        )
    }
}